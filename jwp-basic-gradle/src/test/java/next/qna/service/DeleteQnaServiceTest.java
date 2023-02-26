package next.qna.service;

import java.util.List;
import java.util.Optional;
import next.answer.dao.AnswerDao;
import next.answer.model.Answer;
import next.common.error.DomainException;
import next.common.error.DomainExceptionCode;
import next.qna.dao.QuestionDao;
import next.qna.domain.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class DeleteQnaServiceTest {

    @InjectMocks
    private DeleteQnaService deleteQnaService;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    @Test
    void 삭제할_질문이_존재하지_않는경우_도메인_에러가_발생합니다() {
        BDDMockito.given(questionDao.findOptionalById(1L)).willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> deleteQnaService.execute(1L))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(DomainExceptionCode.DID_NOT_EXISTS_QUESTION_ID.getMessage());
    }

    @Test
    void 질문을_삭제할때_자신이_작성하지_않는_답변이_존재하는_경우() {
        final var question = Question.of("작성자", "", "");
        final var answer = List.of(
                new Answer("작성자", "", 1L),
                new Answer("작성자1", "", 1L),
                new Answer("작성자1", "", 1L),
                new Answer("작성자", "", 1L));

        BDDMockito.given(questionDao.findOptionalById(1L)).willReturn(Optional.of(question));
        BDDMockito.given(answerDao.findAllByQuestionId(1L)).willReturn(answer);

        Assertions.assertThatThrownBy(() -> deleteQnaService.execute(1L))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(DomainExceptionCode.DID_NOT_DELETE_QUESTION.getMessage(2));
    }


    // 리턴이 없는 경우는 어떻게 테스트하지?
    @Test
    void 질문을_삭제할때_자신이_작성한_답변만_존재하는_경우() {
        final var question = Question.of("작성자", "", "");
        final var answer = List.of(
                new Answer("작성자", "", 1L),
                new Answer("작성자", "", 1L),
                new Answer("작성자", "", 1L),
                new Answer("작성자", "", 1L));

        BDDMockito.given(questionDao.findOptionalById(1L)).willReturn(Optional.of(question));
        BDDMockito.given(answerDao.findAllByQuestionId(1L)).willReturn(answer);

        deleteQnaService.execute(1L);
    }

}