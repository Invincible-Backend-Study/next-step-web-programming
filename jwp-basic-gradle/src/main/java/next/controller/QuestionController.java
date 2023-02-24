package next.controller;

import core.web.ModelAndView;
import core.web.View;
import java.util.List;
import javax.servlet.http.HttpSession;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.view.JsonView;
import next.view.JspView;
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

            Question question = new Question(user.getName(),
                    request.getParameter("title"),
                    request.getParameter("contents"));

            question = questionDao.insert(question);
            return new ModelAndView(new JspView("redirect:/question/list"));
        }  catch (SQLException e) {
            log.error(e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return new ModelAndView(new JspView("redirect:/question/list"));
    }
}
