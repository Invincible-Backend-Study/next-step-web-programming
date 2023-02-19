package next.controller.qna.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvcframework.controller.Controller;
import java.io.IOException;
import java.io.PrintWriter;
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
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        long answerId = Long.parseLong(request.getParameter("answerId"));
        int deleteResult = answerService.deleteAnswer(answerId);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            if (deleteResult <= 0) {
                out.print(objectMapper.writeValueAsString(Result.fail("삭제 실패")));
                return null;
            }
            out.print(objectMapper.writeValueAsString(Result.ok()));
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
