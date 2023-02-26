package core.mvcframework.controller;

import core.mvcframework.ModelAndView;
import core.mvcframework.view.JsonView;
import core.mvcframework.view.JspView;

public abstract class AbstractController implements Controller {

    protected ModelAndView jspView(String forwardUri) {
        return new ModelAndView(new JspView(forwardUri));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }

}
