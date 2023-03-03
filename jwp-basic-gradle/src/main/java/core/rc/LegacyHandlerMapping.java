package core.rc;

import com.google.common.collect.Maps;
import core.mvc.Controller;
import core.mvc.ForwardController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import next.answer.controller.AddAnswerController;
import next.answer.controller.DeleteAnswerController;
import next.answer.controller.GetQuestionController;
import next.answer.controller.ShowController;
import next.answer.dao.AnswerDao;
import next.qna.dao.QuestionDao;
import next.qna.service.DeleteQnaService;
import next.qna.ui.CreateQnAFormController;
import next.qna.ui.CreateQnaActionController;
import next.qna.ui.DeleteQnaController;
import next.qna.ui.HomeController;
import next.qna.ui.UpdateQnaActionController;
import next.qna.ui.UpdateQnaFormController;
import next.user.controller.CreateUserController;
import next.user.controller.ListUserController;
import next.user.controller.UpdateFormController;
import next.user.controller.UpdateUserController;
import next.user.controller.UserLoginController;
import next.user.controller.UserLogoutController;

@Slf4j
public class LegacyHandlerMapping implements HandlerMapping {
    private static final String DEFAULT_VIEW_PATH = "/WEB-INF";
    private final Map<String, Controller> mapping = Maps.newHashMap();

    public LegacyHandlerMapping() {
        mapping.put("/", new HomeController());
        mapping.put("/user/form", new ForwardController(DEFAULT_VIEW_PATH + "/user/form.jsp"));
        mapping.put("/user/loginForm", new ForwardController(DEFAULT_VIEW_PATH + "/user/login.jsp"));
        mapping.put("/qna/show", new ShowController());
        // 고민중
        //mapping.put("/user/loginFailed", new ForwardController(DEFAULT_VIEW_PATH + "/user/login_failed.jsp"));
        //mapping.put("/user/signupFailed", new ForwardController(DEFAULT_VIEW_PATH + "/user/signup_failed.jsp"));

        mapping.put("/api/qna/show", new GetQuestionController());
        mapping.put("/qna/form", new CreateQnAFormController());
        mapping.put("/qna/create", new CreateQnaActionController());
        mapping.put("/qna/update", new UpdateQnaActionController());
        mapping.put("/qna/delete", new DeleteQnaController(new DeleteQnaService(new QuestionDao(), new AnswerDao())));
        mapping.put("/qna/update/form", new UpdateQnaFormController());
        mapping.put("/api/qna/addAnswer", new AddAnswerController());
        mapping.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        mapping.put("/user/updateForm", new UpdateFormController());
        mapping.put("/user/list", new ListUserController());
        mapping.put("/user/create", new CreateUserController());
        mapping.put("/user/update", new UpdateUserController());
        mapping.put("/user/login", new UserLoginController());
        mapping.put("/user/logout", new UserLogoutController());
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        return mapping.get(request.getRequestURI());
    }
}
