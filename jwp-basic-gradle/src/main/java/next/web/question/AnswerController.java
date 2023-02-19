package next.web.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.User;
import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AnswerController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            return null;
        }
        Answer as = new Answer(
                user.getName(),req.getParameter("contents"),
                Integer.parseInt(req.getParameter("id"))
        );;
        AnswerDao dao =  new AnswerDao();
        Answer answer =  dao.addAnswer(as);
        res.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = res.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            out.print(mapper.writeValueAsString(answer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
