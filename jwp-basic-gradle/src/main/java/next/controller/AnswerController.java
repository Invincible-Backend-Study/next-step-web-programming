package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.web.View;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;
import next.view.JsonView;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Override
    protected View doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new JspView("redirect:/user/login_failed.jsp");
            }

            Answer answer = new Answer(user.getName(),
                    request.getParameter("contents"),
                    Long.parseLong(request.getParameter("questionId")));

            answer = AnswerDao.insert(answer);
            log.debug(answer.toString());
            return new JsonView(answer);
        }  catch (SQLException e) {
            log.error(e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
