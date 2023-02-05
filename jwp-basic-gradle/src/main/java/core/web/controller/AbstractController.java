package core.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        if (method.equals(HttpMethod.GET)) {
            return doGet(request, response);
        }
        if (method.equals(HttpMethod.POST)) {
            return doPost(request, response);
        }
        throw new IllegalArgumentException("[ERROR] 요청과 일치되는 메소드가 없습니다.");
    }

    // HTTP Method: GET
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        throw new IllegalArgumentException("[ERROR] 정의되지 않은 요청입니다.");
    }

    // HTTP Method: POST
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        throw new IllegalArgumentException("[ERROR] 정의되지 않은 요청입니다.");
    }
}
