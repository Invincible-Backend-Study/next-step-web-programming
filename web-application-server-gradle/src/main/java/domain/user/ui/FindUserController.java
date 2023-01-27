package domain.user.ui;


import common.BasicController;
import domain.user.application.FindUserUseCase;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class FindUserController implements BasicController {
    private final FindUserUseCase findUserUseCase = new FindUserUseCase();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse response) {

    }
}
