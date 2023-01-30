package domain.user.ui;

import common.BasicController;
import domain.common.DomainException;
import domain.user.application.RegisterUserUseCase;
import domain.user.payload.request.RegisterUserRequest;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;

public class RegisterUserController implements BasicController {
    private static final RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse response) throws IOException {
        try {
            registerUserUseCase.execute(this.parserUserRequest(httpRequest));
            response.writeRedirect("/index.html");
        } catch (DomainException domainException) {
            response.writeRedirect(domainException.getRedirectUrl().orElseGet(() -> "/user/form.html"));
        }
    }

    private RegisterUserRequest parserUserRequest(final HttpRequest httpRequest) {
        return RegisterUserRequest.of(
                httpRequest.getParameterByKey("userId"),
                httpRequest.getParameterByKey("password"),
                httpRequest.getParameterByKey("name"),
                httpRequest.getParameterByKey("email")
        );
    }
}
