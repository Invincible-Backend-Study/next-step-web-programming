package next.controller;

import core.web.Controller;
import core.web.HttpMethod;
import core.web.ModelAndView;
import core.web.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class AbstractController implements Controller {
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        HttpMethod method = HttpMethod.from(request.getMethod());
        if (method.isGet()) {
            return doGet(request, response);
        }
        if (method.isPost()) {
            return doPost(request, response);
        }
        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다. (method:" + method.toString());
    }

    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다.");
    }

    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다.");
    }
}
