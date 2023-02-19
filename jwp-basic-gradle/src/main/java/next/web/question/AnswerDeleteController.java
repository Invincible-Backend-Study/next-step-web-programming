package next.web.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import next.dao.AnswerDao;
import next.model.User;
import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AnswerDeleteController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String answerId = req.getParameter("answerId");
        AnswerDao dao =  new AnswerDao();
        if(user == null && answerId == null){
            return null;
        }
        dao.deleteAnswer(answerId);
        res.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = res.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            out.print(mapper.writeValueAsString("성공"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
        return null;
    }
}
