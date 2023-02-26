package next.controller.qna;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.qna.dto.QuestionUpdateFormDto;
import next.model.User;
import next.service.QuestionService;
import next.utils.SessionUtil;

public class QuestionUpdateFormController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        String writer = request.getParameter("writer");
        Long questionId = Long.parseLong(request.getParameter("questionId"));
        validateWriter(writer);
        User user = SessionUtil.getLoginObject(request.getSession(), "user");
        if (writer.equals(user.getUserId())) {
            return jspView("qna/update").addObject(
                    "question",
                    QuestionUpdateFormDto.from(questionService.findById(questionId))
            );
        }
        return jspView("redirect:/");
    }

    private static void validateWriter(final String writer) {
        if (writer == null || writer.equals("")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 접근입니다.");
        }
    }
}
