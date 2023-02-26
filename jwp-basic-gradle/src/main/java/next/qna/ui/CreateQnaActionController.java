package next.qna.ui;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.utils.UserUtils;
import next.qna.payload.request.CreateQuestionRequest;
import next.qna.service.CreateQuestionService;

public class CreateQnaActionController extends AbstractController {
    private final CreateQuestionService createQnaService = CreateQuestionService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserUtils.isLoggedIn(request.getSession())) {
            return this.jspView("redirect: /");
        }

        createQnaService.execute(this.convertHttpRequestToPayloadRequest(request));
        return this.jspView("redirect: /");
    }

    private CreateQuestionRequest convertHttpRequestToPayloadRequest(HttpServletRequest request) {
        return CreateQuestionRequest.of(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );
    }
}
