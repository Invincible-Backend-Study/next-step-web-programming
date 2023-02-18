package next.mvc;

import next.web.ForwardController;
import next.web.HomeController;
import next.web.question.AnswerController;
import next.web.question.QuestionController;
import next.web.question.QuestionFormController;
import next.web.user.*;

import java.util.HashMap;

public class RequestMapping {
    private final HashMap<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/user/login", new UserLoginController());
        mappings.put("/user/profile", new ForwardController("/user/profile.jsp"));
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/updateForm", new UpdateUserFormController());
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/user/list", new UserListController());
        mappings.put("/qna/show", new QuestionController());
        mappings.put("/qna/form", new QuestionFormController());
        mappings.put("/qna/answer", new AnswerController());
    }

    public Controller getController(String url){
        return mappings.get(url);
    }
}
