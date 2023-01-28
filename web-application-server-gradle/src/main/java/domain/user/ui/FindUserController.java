package domain.user.ui;


import common.BasicController;
import domain.user.application.FindUserUseCase;
import domain.user.payload.response.UserInformationResponse;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class FindUserController implements BasicController {
    private final FindUserUseCase findUserUseCase = new FindUserUseCase();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse response) {
        var logined = httpRequest.getCookie("logined")
                .map(Boolean::parseBoolean)
                .orElse(false);

        if (logined) {
            var generatedHtml = this.generateView(findUserUseCase.getAllUser());
            response.writeHtml(generatedHtml);
            return;
        }

        response.writeRedirect("/index.html");
    }

    private String generateView(List<UserInformationResponse> allUser) {
        final var userView = allUser.stream()
                .map(UserInformationResponse::toHTML)
                .collect(Collectors.joining());

        return String.format("<!DOCTYPE HTML><head></head><body><ul>%s</ul></body></html>", userView);


    }
}
