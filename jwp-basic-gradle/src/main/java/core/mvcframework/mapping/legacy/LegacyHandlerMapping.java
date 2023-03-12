package core.mvcframework.mapping.legacy;

import core.mvcframework.controller.Controller;
import core.mvcframework.controller.ForwardController;
import core.mvcframework.mapping.HandlerMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import next.controller.qna.api.legacy.AddAnswerApiController;
import next.controller.qna.api.legacy.DeleteAnswerApiController;
import next.controller.qna.api.legacy.QuestionDeleteApiController;
import next.controller.qna.api.legacy.QuestionListApiController;
import next.controller.qna.legacy.QuestionCreateController;
import next.controller.qna.legacy.QuestionDeleteController;
import next.controller.qna.legacy.QuestionFormController;
import next.controller.qna.legacy.QuestionUpdateController;
import next.controller.qna.legacy.QuestionUpdateFormController;
import next.controller.qna.legacy.ShowController;
import next.controller.user.legacy.ProfileController;
import next.controller.user.legacy.SignOutController;
import next.controller.user.legacy.UpdateUserController;
import next.controller.user.legacy.UpdateUserFormController;
import next.controller.user.legacy.UserListController;
import next.controller.user.legacy.auth.LoginController;
import next.controller.user.legacy.auth.SignUpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(LegacyHandlerMapping.class);

    private final Map<String, Controller> handlerMapping = new HashMap<>();

    @Override
    public void initialize() {
        // 로그인 필요없음
//        handlerMapping.put("/", new HomeController());
        handlerMapping.put("/signUpForm", new ForwardController("user/form"));
        handlerMapping.put("/signUp", new SignUpController());
        handlerMapping.put("/loginForm", new ForwardController("user/login"));
        handlerMapping.put("/login", new LoginController());
        handlerMapping.put("/loginFailed", new ForwardController("user/login_failed"));

//         로그인 필요
        handlerMapping.put("/users", new UserListController());
        handlerMapping.put("/users/signOut", new SignOutController());
        handlerMapping.put("/users/profile", new ProfileController());
        handlerMapping.put("/users/updateForm", new UpdateUserFormController());
        handlerMapping.put("/users/update", new UpdateUserController());
        handlerMapping.put("/qna/questionForm", new QuestionFormController());
        handlerMapping.put("/qna/createQuestion", new QuestionCreateController());
        handlerMapping.put("/qna/updateQuestionForm", new QuestionUpdateFormController());
        handlerMapping.put("/qna/updateQuestion", new QuestionUpdateController());
        handlerMapping.put("/qna/deleteQuestion", new QuestionDeleteController());
        handlerMapping.put("/qna/show", new ShowController());
        handlerMapping.put("/api/qna/deleteQuestion", new QuestionDeleteApiController());
        handlerMapping.put("/api/qna/addAnswer", new AddAnswerApiController());
        handlerMapping.put("/api/qna/deleteAnswer", new DeleteAnswerApiController());
        handlerMapping.put("/api/qna/list", new QuestionListApiController());
    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        log.debug("requestUri={}", request.getRequestURI());
        return handlerMapping.get(request.getRequestURI());
    }

}


