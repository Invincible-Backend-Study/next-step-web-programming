package next.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);

    private Map<String, Controller> mapping = new HashMap<>();


    public void initMapping(){
        mapping.put("/",new ForwardController("/WEB-INF/index.jsp"));
        mapping.put("/users/form", new ForwardController("/WEB-INF/form.jsp"));
        mapping.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mapping.put("/user/updateForm", new ForwardController("/user/update.jsp"));

        mapping.put("/user/list", new ListUserController());
        mapping.put("/user/create", new CreateUserController());
        mapping.put("/user/update" , new UpdateUserController());
        mapping.put("/user/login", new UserLoginController());
        mapping.put("/user/logout", new UserLogoutController());
        log.info("Initialized RequestMapping!");
    }

    public Controller findController(String url){
        return mapping.get(url);
    }

    private void put(String url, Controller controller){
        mapping.put(url,controller);
    }
}
