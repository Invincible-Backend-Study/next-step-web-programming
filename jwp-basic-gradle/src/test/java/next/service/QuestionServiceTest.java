package next.service;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import next.mock.MockAnswerDao;
import next.mock.MockQuestionDao;
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
    private QuestionService questionService;
    @Mock
    private MockAnswerDao answerDao;
    @Mock
    private MockQuestionDao questionDao;

    @Test
    @DisplayName("존재하지 않는 질문을 삭제하려 할 시 예외 테스트")
    public void Test_when_trying_to_delete_a_question_that_doesnt_exist() {
        Assertions.assertThatThrownBy(() -> questionService.deleteQuestion(10, newUser("wnsghs")))
                .hasMessage("해당 게시물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("작성자가 같지 않을 때의 예외 테스트")
    public void Test_that_the_authors_are_not_equal() {
        Question question = newQuestion(12, "wnsgh");
        when(questionDao.findByQuestionId(12)).thenReturn(question);
        Assertions.assertThatThrownBy(() -> questionService.deleteQuestion(12, newUser("wnsghss")))
                .hasMessage("작성자가 아닐 시 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("다른 사용자의 답변이 존재할시 예외 테스트")
    public void If_its_the_same_user_and_there_is_no_answer() {
        Question question = newQuestion(12, "wnsgh");
        Answer answer = newAnswer(12, "다른사용자");
        when(questionDao.findByQuestionId(12)).thenReturn(question);
        when(answerDao.findAllByQuestonId(12)).thenReturn(List.of(answer));
        Assertions.assertThatThrownBy(
                        () -> questionService.deleteQuestion(12, newUser("wnsgh")))
                .hasMessage("답변이 존재합니다.");
        ;
    }

    public Question newQuestion(int questionId, String writer) {
        return new Question(questionId, writer, "제목", "내용", Timestamp.valueOf(LocalDateTime.now()), 1);
    }

    public Answer newAnswer(int questionId, String writer) {
        return new Answer(1, writer, "내용", Timestamp.valueOf(LocalDateTime.now()), questionId);
    }

    public User newUser(String name) {
        return new User("wnsghs", "123", name, "wng@nas");
    }
}