package core.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        log.debug("httpMethod={}", method);
        if (method.isGet()) {
            return doGet(request, response);
        }
        if (method.isPost()) {
            return doPost(request, response);
        }
        throw new IllegalArgumentException("[ERROR] 요청과 일치되는 메소드가 없습니다.");
    }

    // HTTP Method: GET
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        throw new IllegalArgumentException("[ERROR] 정의되지 않은 요청입니다.");
    }

    // HTTP Method: POST
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        throw new IllegalArgumentException("[ERROR] 정의되지 않은 요청입니다.");
    }
}
