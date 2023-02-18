package next.controller;

import core.web.View;
import next.dao.QuestionDao;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ListQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ListQuestionController.class);
    @Override
    protected View doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("questions", QuestionDao.findAll());
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return new JspView("/qna/list.jsp");
    }
}
