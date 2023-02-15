package next.controller;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowFormController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        return "qna/show";
    }
}
