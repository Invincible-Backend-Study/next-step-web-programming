package next.web;

import next.controller.Controller;
import next.controller.CreateUserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> controllers = new HashMap<>();
    static {
        controllers.put("/user/create", new CreateUserController());
    }

    public static Controller getController(String requestUri) {
        return controllers.get(requestUri);
    }
}
