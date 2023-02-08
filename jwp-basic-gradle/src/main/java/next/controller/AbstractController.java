package next.controller;

import next.web.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

abstract public class AbstractController implements Controller {
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpMethod method = HttpMethod.from(request.getMethod());
        if (method.isGet()) {
            return doGet(request, response);
        }
        if (method.isPost()) {
            return doPost(request, response);
        }
        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다. (method:" + method.toString());
    }

    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다.");
    }

    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다.");
    }
}
