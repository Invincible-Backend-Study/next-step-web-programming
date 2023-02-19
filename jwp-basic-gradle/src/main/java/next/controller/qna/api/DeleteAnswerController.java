package next.controller.qna.api;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.Controller;
import core.mvcframework.view.JsonView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.Result;
import next.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
    private final AnswerService answerService = new AnswerService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        long answerId = Long.parseLong(request.getParameter("answerId"));
        int deleteResult = answerService.deleteAnswer(answerId);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        if (deleteResult <= 0) {
            modelAndView.setAttribute("result", Result.fail("삭제 실패"));
            return modelAndView;
        }
        modelAndView.setAttribute("result", Result.ok());
        return modelAndView;
    }
}
