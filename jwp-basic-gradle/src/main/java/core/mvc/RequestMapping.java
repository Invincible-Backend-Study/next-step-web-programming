package core.mvc;

import java.util.HashMap;
import java.util.Map;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final String DEFAULT_VIEW_PATH = "/WEB-INF";
    private final Map<String, AbstractController> mapping = new HashMap<>();

    public void initMapping() {
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

    public Controller findController(String url) {
        return mapping.get(url);
    }

    private void put(String url, AbstractController controller) {
        mapping.put(url, controller);
    }
}
