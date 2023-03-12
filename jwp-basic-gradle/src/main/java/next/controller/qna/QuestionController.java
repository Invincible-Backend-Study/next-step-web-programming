package next.controller.qna;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionCreateDto;
import next.controller.qna.dto.QuestionUpdateFormDto;
import next.exception.CannotDeleteQuestionException;
import next.exception.CannotUpdateQuestionException;
import next.model.User;
import next.service.QuestionService;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class QuestionController extends AbstractAnnotationController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    @Inject
    public QuestionController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/questions/show", method = RequestMethod.GET)
    public ModelAndView questionList(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Map<String, Object> questionWithAnswers = questionService.findByQuestionIdWithAnswers(questionId);
        log.debug("questionWithAnswers={}", questionWithAnswers);
        return jspView("qna/show").addObject("questionWithAnswers", questionWithAnswers);
    }

    @RequestMapping(value = "/questions/new", method = RequestMethod.GET)
    public ModelAndView createQuestionForm(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("qna/form");
    }

    @RequestMapping(value = "/questions/new", method = RequestMethod.POST)
    public ModelAndView createQuestion(final HttpServletRequest request, final HttpServletResponse response) {
        QuestionCreateDto questionCreateDto = QuestionCreateDto.createDto(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );
        questionService.insertNewQuestion(questionCreateDto.toModel());
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/questions/update", method = RequestMethod.GET)
    public ModelAndView updateQuestionForm(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        String writer = request.getParameter("writer");
        try {
            return jspView("qna/update").addObject("question",
                    QuestionUpdateFormDto.from(questionService.findToUpdateQuestion(
                            questionId,
                            writer,
                            SessionUtil.getLoginObject(request.getSession(), "user"))));
        } catch (CannotUpdateQuestionException exception) {
            log.error(exception.getMessage());
            return jspView("redirect:/");
        }
    }

    @RequestMapping(value = "/questions/update", method = RequestMethod.POST)
    public ModelAndView updateQuestion(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            QuestionUpdateFormDto requestDto = QuestionUpdateFormDto.createRequestDto(
                    request.getParameter("questionId"),
                    request.getParameter("writer"),
                    request.getParameter("title"),
                    request.getParameter("contents")
            );
            questionService.updateQuestion(
                    requestDto.toModel(),
                    SessionUtil.getLoginObject(request.getSession(), "user")
            );
            return jspView("redirect:/questions/show?questionId=" + requestDto.getQuestionId());
        } catch (CannotUpdateQuestionException exception) {
            log.error(exception.getMessage());
            return jspView("redirect:/");
        }
    }

    @RequestMapping(value = "/questions/delete", method = RequestMethod.POST)
    public ModelAndView deleteQuestion(final HttpServletRequest request, final HttpServletResponse response) {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        User user = SessionUtil.getLoginObject(request.getSession(), "user");
        try {
            questionService.deleteQuestion(questionId, user);
            return jspView("redirect:/");
        } catch (CannotDeleteQuestionException exception) {
            log.debug("cannotDeleteQuestion={}", exception.getMessage());
            return jspView("qna/show").addObject(
                            "questionWithAnswers",
                            questionService.findByQuestionIdWithAnswers(questionId))
                    .addObject("errorMessage", exception.getMessage());
        }
    }

}
