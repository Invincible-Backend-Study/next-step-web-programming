package next.web;

import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {

    private final String url;

    public ForwardController(String url) {
        this.url = url;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return url;
    }

}
