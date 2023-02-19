package next.web.controller.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import next.dao.AnswerDao;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.Controller;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AnswerDeleteController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String answerId = req.getParameter("answerId");
        AnswerDao dao =  new AnswerDao();
        if(user == null && answerId == null){
            return jsonView();
        }
        dao.deleteAnswer(answerId);
        return jsonView().addObject("result","성공");
    }
}
