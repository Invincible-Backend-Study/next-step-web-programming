package core.mvcframework;

import java.util.HashMap;
import java.util.Map;
import next.web.controller.CreateUserController;
import next.web.controller.HomeController;
import next.web.controller.ListUserController;
import next.web.controller.LoginUserController;
import next.web.controller.LogoutUserController;
import next.web.controller.ProfileController;
import next.web.controller.UpdateUserFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final Map<String, Controller> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put("/", new HomeController());
        handlerMapping.put("/user/create", new CreateUserController());
        handlerMapping.put("/user/list", new ListUserController());
        handlerMapping.put("/user/login", new LoginUserController());
        handlerMapping.put("/user/logout", new LogoutUserController());
        handlerMapping.put("/user/profile", new ProfileController());
        handlerMapping.put("/user/update", new UpdateUserFormController());
    }

    public Controller getHandlerMapping(final String requestURI) {
        log.debug("requestURI={}", requestURI);
        return handlerMapping.get(requestURI);
    }
}
