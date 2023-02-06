package next.mvc;

import next.web.*;

import java.util.HashMap;

public class RequestMapping {
    private HashMap<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/user/login", new UserLoginController());
        mappings.put("/user/profile", new ForwardController("/user/profile.jsp"));
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/updateForm", new UpdateUserFormController());
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/user/list", new ForwardController("/user/list.jsp"));
    }

    public Controller getController(String url){
        return mappings.get(url);
    }
}
