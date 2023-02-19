package next.controller;

import core.mvc.AbstractController;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final String DEFAULT_VIEW_PATH = "/WEB-INF";

    private final Map<String, AbstractController> mapping = new HashMap<>();


    public void initMapping(){
        mapping.put("/", new HomeController());
        mapping.put("/user/form", new ForwardController(DEFAULT_VIEW_PATH + "/user/form.jsp"));
        mapping.put("/user/loginForm", new ForwardController(DEFAULT_VIEW_PATH + "/user/login.jsp"));
        mapping.put("/qna/show", new ShowController());
        // 고민중
        //mapping.put("/user/loginFailed", new ForwardController(DEFAULT_VIEW_PATH + "/user/login_failed.jsp"));
        //mapping.put("/user/signupFailed", new ForwardController(DEFAULT_VIEW_PATH + "/user/signup_failed.jsp"));

        mapping.put("/qna/form", new CreateQnAFormController());
        mapping.put("/qna/create", new CreateQnaActionController());
        mapping.put("/api/qna/addAnswer", new AddAnswerController());
        mapping.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        mapping.put("/user/updateForm", new UpdateFormController());
        mapping.put("/user/list", new ListUserController());
        mapping.put("/user/create", new CreateUserController());
        mapping.put("/user/update" , new UpdateUserController());
        mapping.put("/user/login", new UserLoginController());
        mapping.put("/user/logout", new UserLogoutController());
    }

    public Controller findController(String url){
        return mapping.get(url);
    }

    private void put(String url, AbstractController controller){
        mapping.put(url,controller);
    }
}
