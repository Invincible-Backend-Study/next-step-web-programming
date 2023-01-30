package domain.user.ui;

import common.BasicController;
import domain.user.application.UserLoginUseCase;
import domain.user.payload.request.UserLoginRequest;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;

public class UserLoginController implements BasicController {
    private static final UserLoginUseCase userLoginUseCase = new UserLoginUseCase();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse response) throws IOException {
        final var successLogin = userLoginUseCase.execute(this.convertRequestToLoginRequest(httpRequest));

        if (successLogin) {
            response.writeRedirect("/index.html").write("Set-Cookie: logined=true; Path=/");
            return;
        }

        response.writeRedirect("/user/login_failed.html").write("Set-Cookie: logined=false; Path=/");
    }

    private UserLoginRequest convertRequestToLoginRequest(HttpRequest httpRequest) {
        return UserLoginRequest.of(httpRequest.getParameterByKey("userId"), httpRequest.getParameterByKey("password"));
    }
}
