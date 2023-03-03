package core.web;

import core.nmvc.HandlerMapping;
import next.api.qna.controller.*;
import next.api.web.HomeController;
import next.api.auth.controller.LoginController;
import next.api.auth.controller.LogoutController;
import next.api.user.controller.CreateUserController;
import next.api.user.controller.ListUserController;
import next.api.user.controller.UpdateUserController;
import next.api.user.controller.UpdateUserFormController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class LegacyHandlerMapping implements HandlerMapping {
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

    public Controller getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return controllers.get(requestUri);
    }
}
