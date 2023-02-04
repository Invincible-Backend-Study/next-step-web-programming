package next.web;

import next.controller.Controller;
import next.controller.CreateUserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;

public class RequestMapping {
    private static final Map<String, Controller> controllers = new HashMap<>();
    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/logout", new LogoutController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/form", new UpdateUserFormController());
        controllers.put("/user/update", new UpdateUserController());
    }

    public static Controller getController(String requestUri) {
        return controllers.get(requestUri);
    }
}
