package next.answer.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.utils.UserUtils;

public class ShowController extends AbstractController {
    private final FindQuestionService questionService = FindQuestionService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var questionId = this.convertHttpRequestToQuestionId(request);
        final var findQuestionResponse = questionService.execute(questionId);

        if (findQuestionResponse.getQuestion() == null) {
            // 존재하지 않는 질문의 경우 메인 페이지로 리다이렉트 합니다.
            return this.jspView("redirect: /");
        }

        request.setAttribute("question", findQuestionResponse.getQuestion());
        request.setAttribute("answers", findQuestionResponse.getAnswers());
        request.setAttribute("isLoggedIn", UserUtils.isLoggedIn(request.getSession()));

        return this.jspView("/WEB-INF/qna/show.jsp");
    }

    public Long convertHttpRequestToQuestionId(HttpServletRequest request) {
        try {
            return Long.valueOf(request.getParameter("questionId"));
        } catch (Exception e) {
            return 0L;
        }
    }

}
