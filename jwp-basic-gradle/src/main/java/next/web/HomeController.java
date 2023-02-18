package next.web;

import next.dao.QuestionDao;
import next.model.User;
import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        QuestionDao dao = new QuestionDao();
        req.setAttribute("questions",dao.findAll());
        return "home.jsp";
    }

}
