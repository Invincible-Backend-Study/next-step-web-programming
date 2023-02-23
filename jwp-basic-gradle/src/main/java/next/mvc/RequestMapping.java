package next.mvc;

import next.web.controller.ForwardController;
import next.web.controller.HomeController;
import next.web.controller.question.*;
import next.web.controller.user.*;

import java.util.HashMap;

public class RequestMapping {
    private final HashMap<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/user/login", new UserLoginController());
        mappings.put("/user/profile", new ProfileController());
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/updateForm", new UpdateUserFormController());
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/user/list", new UserListController());
        mappings.put("/qna/show", new QuestionController());
        mappings.put("/qna/form", new QuestionFormController());
        mappings.put("/qna/answer", new AddAnswerController());
        mappings.put("/qna/deleteAnswer", new AnswerDeleteController());
        mappings.put("/api/qna/list", new QnaListController());
    }

    public Controller getController(String url){
        return mappings.get(url);
    }
}
