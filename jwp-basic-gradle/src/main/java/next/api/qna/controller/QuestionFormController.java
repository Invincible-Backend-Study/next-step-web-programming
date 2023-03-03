package next.api.qna.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.web.ModelAndView;
import next.api.qna.model.Question;
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
public class QuestionFormController {
    private static final Logger log = LoggerFactory.getLogger(QuestionFormController.class);
    private final QuestionService questionService = QuestionService.getInstance();

    @RequestMapping("/question/form")
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView(new JsonView())
                    .addModel("result", Result.fail("질문 수정을 위해선 로그인이 필요합니다."));
        }

        Long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionService.getQuestionByQuestionId(questionId);
        if (!user.getName().equals(question.getWriter())) {
            return new ModelAndView(new JsonView())
                    .addModel("result", Result.fail("자신이 작성한 질문만 수정할 수 있습니다."));
        }

        return new ModelAndView(new JspView("/qna/form.jsp")).addModel("question", question);
    }
}
