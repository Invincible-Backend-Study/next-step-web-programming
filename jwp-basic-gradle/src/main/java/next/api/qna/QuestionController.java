package next.api.qna;

import core.web.ModelAndView;

import java.util.List;
import javax.servlet.http.HttpSession;

import next.common.controller.AbstractController;
import next.api.qna.dao.AnswerDao;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Answer;
import next.api.qna.model.Question;
import next.api.user.model.User;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class QuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

    @Override
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        Question question = null;
        List<Answer> answers = null;
        try {
            question = questionDao.findByQuestionId(questionId);
            answers = answerDao.findByQuestionId(questionId);

            request.setAttribute("question", question);
            request.setAttribute("answers", answers);
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return new ModelAndView(new JspView("/qna/show.jsp")).addModel("question", question).addModel("answers", answers);
    }

    @Override
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ModelAndView(new JspView("redirect:/user/login_failed.jsp"));
            }

            Question question = null;
            String title = request.getParameter("title");
            String contents = request.getParameter("contents");
            String questionIdParam = request.getParameter("questionId");

            if (questionIdParam == null || questionIdParam.isEmpty()) {
                // 게시글 신규 등록
                question = new Question(user.getName(), title, contents);
                questionDao.insert(question);
            } else {
                // 게시글 수정
                Long questionId = Long.parseLong(questionIdParam);
                question = questionDao.findByQuestionId(questionId);  //TODO User 객체가 명확해진다면 Form뿐만 아니라 이곳에서도 동일유저인지 검증
                question.putTitleAndContents(title, contents);
                questionDao.update(question);
            }

            return new ModelAndView(new JspView("redirect:/question/list"));
        }  catch (SQLException e) {
            log.error(e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return new ModelAndView(new JspView("redirect:/question/list"));
    }
}
