package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.Controller;

public abstract class AbstractController implements Controller {

    protected ModelAndView jspView(String forwardUrl){
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView(){
        return new ModelAndView(new JsonView());
    }
}
