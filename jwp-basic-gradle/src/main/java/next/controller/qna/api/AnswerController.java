package next.controller.qna.api;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.Answer;
import next.model.Result;
import next.model.User;
import next.service.AnswerService;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AnswerController extends AbstractAnnotationController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    private final AnswerService answerService;

    @Inject
    public AnswerController(final AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(value = "/api/answers/add", method = RequestMethod.POST)
    public ModelAndView createAnswer(final HttpServletRequest request, final HttpServletResponse response) {
        User user = SessionUtil.getLoginObject(request.getSession(), "user");
        Answer answer = new Answer(
                user.getUserId(),
                request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId")));
        log.debug("answer={}", answer);

        Answer savedAnswer = answerService.insertAnswer(answer);

        return jsonView().addObject("answer", savedAnswer)
                .addObject("result", Result.ok());
    }

    @RequestMapping(value = "/api/answers/delete", method = RequestMethod.POST)
    public ModelAndView deleteAnswer(final HttpServletRequest request, final HttpServletResponse response) {
        long answerId = Long.parseLong(request.getParameter("answerId"));
        int deleteResult = answerService.deleteAnswer(answerId);
        log.debug("deleteResult={}", deleteResult);
        if (deleteResult <= 0) {
            return jsonView().addObject("result", Result.fail("삭제 실패"));
        }
        return jsonView().addObject("result", Result.ok());
    }

}
