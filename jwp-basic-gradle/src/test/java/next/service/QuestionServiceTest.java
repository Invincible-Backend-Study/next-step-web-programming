package next.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import next.mock.MockAnswerDao;
import next.mock.MockQuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionServiceTest {
    private QuestionService questionService;
    private MockAnswerDao answerDao;
    private MockQuestionDao questionDao;

    @BeforeEach
    public void setup() {
        answerDao = new MockAnswerDao();
        questionDao = new MockQuestionDao();
        questionService = new QuestionService(answerDao, questionDao);
    }

    @Test
    @DisplayName("존재하지 않는 질문을 삭제하려 할 시 예외 테스트")
    public void Test_when_trying_to_delete_a_question_that_doesnt_exist() {
        User user = new User("wnsghs", "123", "wnsghs", "wng@nas");
        Assertions.assertThatThrownBy(() -> questionService.deleteQuestion(10, user))
                .hasMessage("해당 게시물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("작성자가 같지 않을 때의 예외 테스트")
    public void Test_that_the_authors_are_not_equal() {
        Question question = new Question(12, "wnsgh", "제목", "내용", Timestamp.valueOf(LocalDateTime.now()), 1);
        User user = new User("wnsghs", "123", "wnsghs", "wng@nas");
        questionDao.addQuestion(question);
        Assertions.assertThatThrownBy(() -> questionService.deleteQuestion(12, user))
                .hasMessage("작성자가 아닐 시 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("다른 사용자의 답변이 존재할시 예외 테스트")
    public void If_its_the_same_user_and_there_is_no_answer() {
        Question question = new Question(12, "wnsgh", "제목", "내용", Timestamp.valueOf(LocalDateTime.now()), 1);
        User user = new User("wnsghs", "123", "wnsgh", "wng@nas");
        Answer answer = new Answer(1, "다른작성자", "내용", Timestamp.valueOf(LocalDateTime.now()), 12);
        answerDao.addAnswer(answer);
        questionDao.addQuestion(question);
        Assertions.assertThatThrownBy(
                        () -> questionService.deleteQuestion(12, user))
                .hasMessage("답변이 존재합니다.");
        ;
    }
}