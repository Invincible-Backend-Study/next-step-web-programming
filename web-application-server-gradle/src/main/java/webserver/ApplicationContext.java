package webserver;

import common.BasicController;
import domain.user.ui.FindUserController;
import domain.user.ui.RegisterUserController;
import domain.user.ui.UserLoginController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ApplicationContext {
    private static final Map<String, BasicController> previousMappingController = new LinkedHashMap<>();

    static {
        previousMappingController.put("/user/create", new RegisterUserController());
        previousMappingController.put("/user/login", new UserLoginController());
        previousMappingController.put("/user/list", new FindUserController());
        //previousMappingController.put("/user")
    }

    public static void process(HttpRequest request, HttpResponse response) throws IOException {
        var requestPath = request.getStartLine().getUrl();

        var findController = Optional.ofNullable(previousMappingController.getOrDefault(requestPath, null));

        findController.orElseGet(() -> ((httpRequest, httpResponse) -> {
            // handle error or html file
            response.write("HTTP/1.1 200 OK ")
                    .writeFile(requestPath);
        })).process(request, response);

        response.send();
    }
}
