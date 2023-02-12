package next.web;

import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {

    private final String url;

    public ForwardController(String url) {
        if(url == null){
            throw new NullPointerException("[ERROR]url을 입력하세요");
        }
        this.url = url;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return url;
    }

}
