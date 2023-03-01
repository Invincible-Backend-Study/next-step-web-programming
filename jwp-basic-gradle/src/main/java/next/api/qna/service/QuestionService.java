package next.api.qna.service;

import core.web.ModelAndView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import next.api.qna.dao.AnswerDao;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Answer;
import next.api.qna.model.Question;
import next.api.user.model.User;
import next.common.model.Result;
import next.common.view.JsonView;

public class QuestionService {
    private static QuestionService questionService = new QuestionService();
    private QuestionService() {};
    public static QuestionService getInstance() {return questionService;};

    private final AnswerDao answerDao = AnswerDao.getInstance();
    private final QuestionDao questionDao = QuestionDao.getInstance();

    public void deleteQuestion(long questionId, User user) {
        Question question = questionDao.findByQuestionId(questionId);
        if (question == null) {
            throw new IllegalArgumentException("해당 번호의 질문이 없습니다.");
        }
        if (!user.getName().equals(question.getWriter())) {
            throw new IllegalArgumentException("자신이 작성한 질문만 삭제할 수 있습니다.");
        }

        List<Answer> answers = answerDao.findByQuestionId(questionId);
        if (!answers.isEmpty()) {
            boolean anotherUserAnswer = answers.stream().anyMatch(answer -> !answer.getWriter().equals(question.getWriter()));
            if (anotherUserAnswer) {
                throw new IllegalArgumentException("다른사람에 답변이 있으면 제거할 수 없습니다.");
            }
        }

        int result = questionDao.deleteByQuestionId(questionId);
        if (result != 1) {
            throw new IllegalArgumentException("정상적으로 반영되지 않았습니다. (변동:" + result + "건)");
        }
        answerDao.deleteByQuestionId(questionId);
    }

    public List<Question> getQuestions() {
        return questionDao.findAll();
    }

    public void putArticle(User user, String title, String contents, String questionIdParam) {
        Question question = null;
        if (questionIdParam == null || questionIdParam.isEmpty()) {
            // 게시글 신규 등록
            question = new Question(user.getName(), title, contents);
            questionDao.insert(question);
        } else {
            // 게시글 수정
            Long questionId = Long.parseLong(questionIdParam);
            question = questionDao.findByQuestionId(questionId);
            question.putTitleAndContents(title, contents);
            questionDao.update(question);
        }
    }

    public Question getQuestionByQuestionId(Long questionId) {
        return questionDao.findByQuestionId(questionId);
    }

    // ANSWER
    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerDao.findByQuestionId(questionId);
    }

    public Answer addAnswer(Answer answer) {
        answer = answerDao.insert(answer);
        Question question = questionDao.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionDao.update(question);
        return answer;
    }

    public boolean deleteAnswer(HttpServletRequest request, User user) {
        Long answerId = Long.parseLong(request.getParameter("answerId"));
        Answer targetAnswer = answerDao.findByAnswerId(answerId);
        if (!user.getName().equals(targetAnswer.getWriter())) {
            return true;
        }

        answerDao.deleteByAnswerId(answerId);
        Question question = questionDao.findByQuestionId(targetAnswer.getQuestionId());
        question.decreaseCountOfAnswer();
        questionDao.update(question);
        return false;
    }
}
