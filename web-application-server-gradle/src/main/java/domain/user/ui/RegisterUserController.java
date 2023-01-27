package domain.user.ui;

import common.BasicController;
import domain.user.application.RegisterUserUseCase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;

public class RegisterUserController implements BasicController {
    private static final RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse response) throws IOException {
        registerUserUseCase.execute();
//            responseRedirectHeader(dataOutputStream, "/index.html");
    }
}
