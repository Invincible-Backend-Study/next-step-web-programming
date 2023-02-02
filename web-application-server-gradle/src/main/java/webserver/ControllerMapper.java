package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.DefaultController;
import controller.LoginController;
import controller.UserListController;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerMapper {
    private static final Logger log = LoggerFactory.getLogger(ControllerMapper.class);

    private static Map<String, Controller> controllers = new HashMap<String, Controller>();
    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
    }

    public static Controller getController(String requestUrl) {
        Controller controller = controllers.get(requestUrl);
        if (controller == null) {
            // html, css, js ... etc
            return new DefaultController();
        }
        return controller;
    }
}
