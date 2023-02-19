package next.controller;

import core.web.ModelAndView;
import core.web.View;
import java.util.List;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class QuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Override
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        Question question = null;
        List<Answer> answers = null;
        try {
            question = QuestionDao.findByQuestionId(questionId);
            answers = AnswerDao.findByQuestionId(questionId);

            request.setAttribute("question", question);
            request.setAttribute("answers", answers);
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return new ModelAndView(new JspView("/qna/show.jsp")).addModel("question", question).addModel("answers", answers);
    }
}
