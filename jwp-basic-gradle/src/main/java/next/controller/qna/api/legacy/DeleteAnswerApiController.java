package next.controller.qna.api.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.Result;
import next.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteAnswerApiController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerApiController.class);

    private final AnswerService answerService;

    public DeleteAnswerApiController(final AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        long answerId = Long.parseLong(request.getParameter("answerId"));
        int deleteResult = answerService.deleteAnswer(answerId);
        log.debug("deleteResult={}", deleteResult);
        if (deleteResult <= 0) {
            return jsonView().addObject("result", Result.fail("삭제 실패"));
        }
        return jsonView().addObject("result", Result.ok());
    }

}
