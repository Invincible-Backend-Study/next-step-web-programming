package next.controller.qna.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvcframework.controller.Controller;
import core.mvcframework.view.JsonView;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.Answer;
import next.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
    private final AnswerService answerService = new AnswerService();

    @Override
    public JsonView execute(final HttpServletRequest request, final HttpServletResponse response) {
        Answer answer = new Answer(
                request.getParameter("writer"),
                request.getParameter("contents"),
                new Date(),
                Long.parseLong(request.getParameter("questionId")));
        log.debug("answer={}", answer);

        Answer savedAnswer = answerService.insertAnswer(answer);
        request.setAttribute("answer", savedAnswer);
        return new JsonView();
    }
}
