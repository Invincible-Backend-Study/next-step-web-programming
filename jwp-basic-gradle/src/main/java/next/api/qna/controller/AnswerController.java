package next.api.qna.controller;

import core.annotation.Controller;
import core.web.ModelAndView;
import next.api.qna.service.QuestionService;
import next.common.controller.AbstractController;
import next.api.qna.dao.AnswerDao;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Answer;
import next.api.qna.model.Question;
import next.common.model.Result;
import next.api.user.model.User;
import next.common.view.JsonView;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class AnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    private final QuestionService questionService = QuestionService.getInstance();

    @Override
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView(new JspView("redirect:/user/login_failed.jsp"));
        }

        Answer answer = new Answer(user.getName(),
                request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId")));

        answer = questionService.addAnswer(answer);
        return new ModelAndView(new JsonView()).addModel("answer", answer);
    }

    @Override
    protected ModelAndView doDelete(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView(new JsonView())
                    .addModel("result", Result.fail("답변 삭제를 위해선 로그인이 필요합니다."));
        }

        if (questionService.deleteAnswer(request, user)) {
            return new ModelAndView(new JsonView())
                    .addModel("result", Result.fail("자신이 작성한 답변만 삭제할 수 있습니다."));
        }
        return new ModelAndView(new JsonView()).addModel("result", Result.ok());
    }
}
