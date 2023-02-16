package core.mvcframework;

import java.util.HashMap;
import java.util.Map;
import next.controller.HomeController;
import next.controller.qna.AnswerCreateController;
import next.controller.qna.QuestionCreateController;
import next.controller.qna.QuestionFormController;
import next.controller.qna.ShowController;
import next.controller.user.CreateUserController;
import next.controller.user.ListUserController;
import next.controller.user.LoginUserController;
import next.controller.user.LogoutUserController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateUserController;
import next.controller.user.UpdateUserFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final Map<String, Controller> handlerMapping = new HashMap<>();

    static {
        // 홈
        handlerMapping.put("/", new HomeController());

        // 유저 단순 포워딩
        handlerMapping.put("/users/form", new ForwardController("user/form"));
        handlerMapping.put("/users/loginForm", new ForwardController("user/login"));
        handlerMapping.put("/users/loginFailed", new ForwardController("user/login_failed"));

        // 유저
        handlerMapping.put("/users/create", new CreateUserController());
        handlerMapping.put("/users", new ListUserController());
        handlerMapping.put("/users/login", new LoginUserController());
        handlerMapping.put("/users/logout", new LogoutUserController());
        handlerMapping.put("/users/profile", new ProfileController());
        handlerMapping.put("/users/update", new UpdateUserController());
        handlerMapping.put("/users/updateForm", new UpdateUserFormController());

        // 질문/응답
        handlerMapping.put("/qna/questionForm", new QuestionFormController());
        handlerMapping.put("/qna/createQuestion", new QuestionCreateController());
        handlerMapping.put("/qna/showForm", new ShowController());
        handlerMapping.put("/qna/createAnswer", new AnswerCreateController());
    }

    public Controller getHandlerMapping(final String requestURI) {
        log.debug("requestURI={}", requestURI);
        return handlerMapping.get(requestURI);
    }
}
