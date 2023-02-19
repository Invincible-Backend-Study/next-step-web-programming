package next.controller;

import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JPanel;
import next.service.QuestionService;

public class HomeController implements Controller {
    private final QuestionService questionService = new QuestionService();

    @Override
    public JspView execute(final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("questions", questionService.findAllOrderByCreatedDate());
        return new JspView("home");
    }
}
