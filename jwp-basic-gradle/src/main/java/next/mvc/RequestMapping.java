package next.mvc;

import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.nmvc.HandlerMapping;
import next.service.AnswerService;
import next.service.QuestionService;
import next.web.controller.ForwardController;
import next.web.controller.HomeController;
import next.web.controller.question.*;
import next.web.controller.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public class RequestMapping implements HandlerMapping {
    private final HashMap<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        AnswerDao answerDao = new JdbcAnswerDao();
        QuestionDao questionDao = new JdbcQuestionDao();
        QuestionService questionService = new QuestionService(answerDao, questionDao);
        AnswerService answerService = new AnswerService(answerDao, questionDao);

        mappings.put("/", new HomeController(questionDao));
        mappings.put("/user/login", new UserLoginController());
        mappings.put("/user/profile", new ProfileController());
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/updateForm", new UpdateUserFormController());
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/user/list", new UserListController());
        mappings.put("/qna/show", new QuestionController(questionService, answerService));
        mappings.put("/qna/form", new QuestionFormController(questionService));
        mappings.put("/qna/answer", new AddAnswerController(answerService));
        mappings.put("/qna/deleteAnswer", new AnswerDeleteController(answerService));
        mappings.put("/qna/deleteQuestion", new DeleteQuestion(questionService));
        mappings.put("/api/qna/list", new QnaListController(questionService));
        mappings.put("/qna/updateQuestion", new QnaUpdateController(questionService));
    }


    @Override
    public Object getHandler(HttpServletRequest req) {
        return mappings.get(req.getRequestURI());
    }
}
