package next.controller;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.service.QuestionService;

public class HomeController implements Controller {
    private final QuestionService questionService = new QuestionService();
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("questions", questionService.findAll());
        return "home";
    }
}
