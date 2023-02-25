package core.web;

import next.common.controller.HomeController;
import next.controller.*;
import next.api.auth.LoginController;
import next.api.auth.LogoutController;
import next.api.qna.AnswerController;
import next.api.qna.ListQuestionController;
import next.api.qna.QuestionController;
import next.api.qna.QuestionFormController;
import next.api.user.CreateUserController;
import next.api.user.ListUserController;
import next.api.user.UpdateUserController;
import next.api.user.UpdateUserFormController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> controllers = new HashMap<>();
    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/logout", new LogoutController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/form", new UpdateUserFormController());
        controllers.put("/user/update", new UpdateUserController());
        controllers.put("/question/list", new ListQuestionController());
        controllers.put("/question", new QuestionController());
        controllers.put("/question/form", new QuestionFormController());
        controllers.put("/answer", new AnswerController());
        controllers.put("/index", new HomeController());
    }

    public static Controller getController(String requestUri) {
        return controllers.get(requestUri);
    }
}
