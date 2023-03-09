package next.api.qna.controller;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.web.ModelAndView;
import next.api.qna.model.Answer;
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
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;
    @Inject
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping("/questions")
    public ModelAndView questionGet(HttpServletRequest request, HttpServletResponse response) {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        Question question = questionService.getQuestionByQuestionId(questionId);
        List<Answer> answers = questionService.getAnswersByQuestionId(questionId);

        return new ModelAndView(new JspView("/qna/show.jsp")).addModel("question", question).addModel("answers", answers);
    }

    @RequestMapping("/questions/list")  //TODO pathVariable 적용 후 "/questions"로 변경
    public ModelAndView questionList(HttpServletRequest request, HttpServletResponse response) {
        List<Question> questions = questionService.getQuestions();
        return new ModelAndView(new JspView("/qna/list.jsp")).addModel("questions", questions);
    }

    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public ModelAndView questionAdd(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView(new JspView("redirect:/user/login_failed.jsp"));
        }

        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        String questionIdParam = request.getParameter("questionId");

        questionService.putArticle(user, title, contents, questionIdParam);

        return new ModelAndView(new JspView("redirect:/questions/list"));
    }

    @RequestMapping(value = "/questions", method = RequestMethod.DELETE)
    public ModelAndView questionRemove(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ModelAndView(new JsonView())
                        .addModel("result", Result.fail("질문 삭제를 위해선 로그인이 필요합니다."));
            }

            Long questionId = Long.parseLong(request.getParameter("questionId"));
            questionService.deleteQuestion(questionId, user);

            return new ModelAndView(new JsonView()).addModel("result", Result.ok());
        } catch (IllegalArgumentException e) {
            return new ModelAndView(new JsonView())
                    .addModel("result", Result.fail(e.getMessage()));
        }
    }

    @RequestMapping("/questions/form")
    public ModelAndView questionGetForm(HttpServletRequest request, HttpServletResponse response) {
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
