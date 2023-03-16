package next.api.qna.service;

import next.api.qna.dao.AnswerDao;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Question;
import next.api.user.model.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock
    private QuestionDao questionDao;
    @Mock
    private AnswerDao answerDao;

    private QuestionService questionService;

    @BeforeEach
    public void setup() {
        questionService = new QuestionService(questionDao, answerDao);
    }

    @Test
    void deleteQuestion_없는질문() {
        when(questionDao.findByQuestionId(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            questionService.deleteQuestion(1L, new User("userId", "pw", "name", "email"));
        });
    }

    @Test
    void deleteQuestion_다른사용자_답변없음() throws Exception {
        Question question = new Question("writer", "title", "contents");
        when(questionDao.findByQuestionId(1L)).thenReturn(question);
        // when(answerDao.findByQuestionId(1L)).thenReturn(Collections.emptyList());  // 어차피 조회되는 값이 없으면 빈리스트가 반환되기 때문에 정의안해줘도 됨

        assertThrows(IllegalArgumentException.class, () -> {
            questionService.deleteQuestion(1L, new User("userId", "pw", "name", "email"));
        });
    }

    @Test
    void deleteQuestion_같은사용자_답변없음() throws Exception {
        Question question = new Question("userName", "title", "contents");
        when(questionDao.findByQuestionId(1L)).thenReturn(question);
        when(answerDao.findByQuestionId(1L)).thenReturn(Lists.newArrayList());
        when(questionDao.deleteByQuestionId(1L)).thenReturn(1);

        questionService.deleteQuestion(1L, new User("userId", "pw", "userName", "email"));
    }

    @Test
    void getQuestions() {
    }

    @Test
    void putArticle() {
    }

    @Test
    void getQuestionByQuestionId() {
    }

    @Test
    void getAnswersByQuestionId() {
    }

    @Test
    void addAnswer() {
    }

    @Test
    void deleteAnswer() {
    }
}