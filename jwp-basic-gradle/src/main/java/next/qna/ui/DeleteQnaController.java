package next.qna.ui;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import next.common.error.DomainException;
import next.qna.service.DeleteQnaService;


@Slf4j
@RequiredArgsConstructor
public class DeleteQnaController extends AbstractController {
    private final DeleteQnaService deleteQnaService;

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var questionId = request.getParameter("questionId");
        // 질문이 null 인 경우
        if (questionId == null) {
            return this.jspView("redirect: /");
        }

        try {
            deleteQnaService.execute(Long.parseLong(questionId));
            return this.jspView("redirect: /");
        } catch (DomainException e) {
            log.error("{}", e.getMessage());
            return this.jspView("redirect: /qna/show?questionId=" + questionId);
        }
    }
}
