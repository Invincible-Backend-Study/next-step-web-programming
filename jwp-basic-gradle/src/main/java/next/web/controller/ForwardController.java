package next.web.controller;

import next.mvc.AbstractController;
import next.mvc.Controller;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController extends AbstractController {

    private final String url;

    public ForwardController(String url) {
        if(url == null){
            throw new NullPointerException("[ERROR]url을 입력하세요");
        }
        this.url = url;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        return jspView(url);
    }

}
