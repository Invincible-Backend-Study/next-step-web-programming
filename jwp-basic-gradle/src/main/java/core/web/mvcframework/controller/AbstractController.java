package core.web.mvcframework.controller;

import core.web.mvcframework.ModelAndView;
import core.web.mvcframework.view.JsonView;
import core.web.mvcframework.view.JspView;

public abstract class AbstractController implements Controller {

    protected ModelAndView jspView(String forwardUri) {
        return new ModelAndView(new JspView(forwardUri, "/WEB-INF/views/", ".jsp"));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }

}
