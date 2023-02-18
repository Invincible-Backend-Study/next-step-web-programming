package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;
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
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/user/login_failed.jsp";
            }

            Answer answer = new Answer(user.getName(),
                    request.getParameter("contents"),
                    Long.parseLong(request.getParameter("questionId")));

            answer = AnswerDao.insert(answer);
            log.debug(answer.toString());
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(mapper.writeValueAsString(answer));
        }  catch (SQLException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
