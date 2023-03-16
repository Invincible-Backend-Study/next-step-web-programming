package next.api.qna.controller;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import next.api.qna.model.Answer;
import next.api.qna.service.QuestionService;
import next.api.user.model.User;
import next.common.model.Result;
import next.common.view.JsonView;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    private final QuestionService questionService;
    @Inject
    public AnswerController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ModelAndView answerAdd(HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping(value = "/answer", method = RequestMethod.DELETE)
    public ModelAndView answerRemove(HttpServletRequest request, HttpServletResponse response) {
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
