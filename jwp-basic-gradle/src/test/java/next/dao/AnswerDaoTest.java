package next.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import next.mock.MockAnswerDao;
import next.mock.MockQuestionDao;
import next.model.Question;
import next.model.User;
import next.service.QuestionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
  
class AnswerDaoTest {
    private QuestionService questionService;
    @Mock
    private MockAnswerDao answerDao;
    @Mock
    private MockQuestionDao questionDao;
    private User user;

    private Question question;

    @BeforeEach
    public void setup() {
        questionService = new QuestionService(answerDao, questionDao);
        question = new Question(12, "wnsgh", "제목", "내용", Timestamp.valueOf(LocalDateTime.now()), 1);
        user = new User("wnsghs", "123", "wnsghs", "wng@nas");
    }

    @Test
    @DisplayName("존재하지 않는 질문을 삭제하려 할 시 예외 테스트")
    public void Test_when_trying_to_delete_a_question_that_doesnt_exist() {
        Assertions.assertThatThrownBy(() -> questionService.deleteQuestion(10, user)).hasMessage("해당 게시물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("작성자가 같지 않을 때의 예외 테스트")
    public void Test_that_the_authors_are_not_equal() {
        questionDao.addQuestion(question);
        Assertions.assertThatThrownBy(() -> questionService.deleteQuestion(12, user))
                .hasMessage("작성자가 아닐 시 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("다른 사용자의 답변이 존재할시 예외 테스트")
    public void If_its_the_same_user_and_there_is_no_answer() {
        Assertions.assertThatThrownBy(
                        () -> questionService.deleteQuestion(8, new User("wnsghs", "123", "wnsgh", "wng@nas")))
                .hasMessage("답변이 존재합니다.");
        ;
    }
}