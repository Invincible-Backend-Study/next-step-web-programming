package next.controller;

import core.web.View;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class QuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Override
    protected View doGet(HttpServletRequest request, HttpServletResponse response) {
        Long questionId = Long.parseLong(request.getParameter("questionId"));
        try {
            request.setAttribute("question", QuestionDao.findByQuestionId(questionId));
            request.setAttribute("answers", AnswerDao.findByQuestionId(questionId));
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return new JspView("/qna/show.jsp");
    }
}
