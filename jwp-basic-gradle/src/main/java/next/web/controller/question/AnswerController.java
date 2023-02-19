package next.web.controller.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.Controller;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            return jsonView();
        }
        Answer as = new Answer(
                user.getName(),req.getParameter("contents"),
                Integer.parseInt(req.getParameter("id"))
        );;
        AnswerDao dao =  new AnswerDao();
        Answer answer =  dao.addAnswer(as);
        res.setContentType("application/json;charset=UTF-8");
        return jsonView().addObject("answer",answer);
    }
}
