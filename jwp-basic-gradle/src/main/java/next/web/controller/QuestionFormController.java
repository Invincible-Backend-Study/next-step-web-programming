package next.web.controller;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionFormController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        return "qna/form";
    }
}
