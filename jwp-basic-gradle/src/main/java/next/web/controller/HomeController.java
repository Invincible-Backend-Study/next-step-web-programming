package next.web.controller;

import next.dao.QuestionDao;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends AbstractController {

    private final QuestionDao dao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("questions",dao.findAll());
        return jspView("home.jsp");
    }

}
