package core.jdbc;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ForwardController extends AbstractController {
    private final String forwardUrl;

    public ForwardController(String forwardUrl) {
        if(forwardUrl == null){
            throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요");
        }
        this.forwardUrl = forwardUrl;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        return this.jspView(forwardUrl);
    }
}
