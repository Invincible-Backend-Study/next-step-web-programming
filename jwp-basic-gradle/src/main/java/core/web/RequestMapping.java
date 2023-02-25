package core.web;

import next.api.qna.*;
import next.common.controller.HomeController;
import next.api.auth.LoginController;
import next.api.auth.LogoutController;
import next.api.user.CreateUserController;
import next.api.user.ListUserController;
import next.api.user.UpdateUserController;
import next.api.user.UpdateUserFormController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> controllers = new HashMap<>();
    static {
        // auth
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/logout", new LogoutController());

        // user
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/form", new UpdateUserFormController());
        controllers.put("/user/update", new UpdateUserController());

        // qna
        controllers.put("/api/qna/list", new ListQuestionApiController());
        controllers.put("/question/list", new ListQuestionController());
        controllers.put("/question", new QuestionController());
        controllers.put("/question/form", new QuestionFormController());
        controllers.put("/answer", new AnswerController());

        // home
        controllers.put("/index", new HomeController());
    }

    public static Controller getController(String requestUri) {
        return controllers.get(requestUri);
    }
}
