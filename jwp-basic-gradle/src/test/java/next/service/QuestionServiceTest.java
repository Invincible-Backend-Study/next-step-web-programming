package next.service;

import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.exception.CannotDeleteQuestionException;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    JdbcQuestionDao questionDao;

    @Mock
    JdbcAnswerDao answerDao;

    @Test
    @DisplayName("존재하지 않는 사용자 질문 삭제시 예외 발생 테스트")
    void deleteNotExistQuestion_fail_withException() {
        // then
        Assertions.assertThatThrownBy(
                        () -> questionService.deleteQuestion(1L, new User("test", "1234", "name", "test@test.com")))
                .isInstanceOf(CannotDeleteQuestionException.class)
                .hasMessageContaining("존재하지 않는 질문글은 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("타인이 작성한 질문 삭제시 예외 발생 테스트")
    void deleteNotSameUserQuestion_fail_withException() {
        // given
        given(answerDao.findAllByQuestionId(1L)).willReturn(Collections.emptyList());
        given(questionDao.findById(1L)).willReturn(new Question(1L, "differentWriter", "test", "test", new Date(), 0));

        // then
        Assertions.assertThatThrownBy(
                        () -> questionService.deleteQuestion(1L, new User("test", "1234", "name", "test@test.com")))
                .isInstanceOf(CannotDeleteQuestionException.class)
                .hasMessageContaining("다른 사용자의 글은 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("다른 사용자의 질문이 달려있는 질문 삭제시 예외 발생 테스트")
    void deleteQuestionWithOtherUserAnswer_fail_withException() {
        // given
        given(answerDao.findAllByQuestionId(1L)).willReturn(
                List.of(new Answer(1L, "differentWriter", "test", new Date(), 1L)));
        given(questionDao.findById(1L)).willReturn(new Question(1L, "test", "test", "test", new Date(), 0));

        // then
        Assertions.assertThatThrownBy(
                        () -> questionService.deleteQuestion(1L, new User("test", "1234", "name", "test@test.com")))
                .isInstanceOf(CannotDeleteQuestionException.class)
                .hasMessageContaining("질문에 다른 사용자의 답변이 달려있으므로 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("답변이 없는 사용자의 질문 삭제시 정상 동작 테스트")
    void deleteQuestionWithNoAnswer_success() {
        // given
        given(answerDao.findAllByQuestionId(1L)).willReturn(Collections.emptyList());
        given(questionDao.findById(1L)).willReturn(new Question(1L, "test", "test", "test", new Date(), 0));

        // then
        Assertions.assertThatNoException().isThrownBy(
                () -> questionService.deleteQuestion(1L, new User("test", "1234", "name", "test@test.com")));
    }

    @Test
    @DisplayName("자신이 답변을 단 질문 삭제시 정상 동작 테스트")
    void deleteQuestionWithAnswers_success() {
        // given
        given(answerDao.findAllByQuestionId(1L)).willReturn(List.of(new Answer(1L, "test", "test", new Date(), 1L)));
        given(questionDao.findById(1L)).willReturn(new Question(1L, "test", "test", "test", new Date(), 0));

        // then
        Assertions.assertThatNoException().isThrownBy(
                () -> questionService.deleteQuestion(1L, new User("test", "1234", "name", "test@test.com")));
    }

}