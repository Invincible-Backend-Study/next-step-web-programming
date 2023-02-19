package core.mvc;

import core.jdbc.Controller;

public abstract class AbstractController implements Controller {

    protected ModelAndView jspView(String forwardUrl){
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView(){
        return new ModelAndView(new JsonView());
    }
}
