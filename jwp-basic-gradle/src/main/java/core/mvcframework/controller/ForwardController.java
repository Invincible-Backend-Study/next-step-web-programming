package core.mvcframework.controller;

import core.mvcframework.controller.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private final String forwardUri;

    public ForwardController(final String forwardUri) {
        if (forwardUri == null) {
            throw new IllegalArgumentException("[ERROR] 포워드할 URI를 등록해야 합니다.");
        }
        this.forwardUri = forwardUri;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        return forwardUri;
    }
}
