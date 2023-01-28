package common;

import domain.user.ui.FindUserController;
import domain.user.ui.RegisterUserController;
import domain.user.ui.UserLoginController;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class PreviousMappingController {
    private static final Map<String, BasicController> previousMappingController = new LinkedHashMap<>();

    static {
        previousMappingController.put("/user/create", new RegisterUserController());
        previousMappingController.put("/user/login", new UserLoginController());
        previousMappingController.put("/user/list", new FindUserController());
    }


    public Optional<BasicController> findControllerBy(String requestPath) {
        return Optional.ofNullable(previousMappingController.getOrDefault(requestPath, null));
    }

    // 자원을 처리하는 컨트롤러
    public BasicController getResourceHandleController() {
        return (httpRequest, httpResponse) -> httpResponse.write("HTTP/1.1 200 OK").writeFile(httpRequest.getRequestPath());
    }
}
