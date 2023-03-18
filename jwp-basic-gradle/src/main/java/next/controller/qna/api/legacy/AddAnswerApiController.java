package next.controller.qna.api.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.Answer;
import next.model.Result;
import next.model.User;
import next.service.AnswerService;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddAnswerApiController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AddAnswerApiController.class);

    private final AnswerService answerService;

    public AddAnswerApiController(final AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
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

}
