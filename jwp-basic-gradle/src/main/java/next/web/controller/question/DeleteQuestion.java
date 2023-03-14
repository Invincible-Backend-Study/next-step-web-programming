package next.web.controller.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.QuestionService;

public class DeleteQuestion extends AbstractController {

    private final QuestionService questionService;

    public DeleteQuestion(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            questionService.deleteQuestion(Integer.parseInt(req.getParameter("questionId")), user);
            return jsonView().addObject("success", "삭제 완료");
        } catch (Exception e) {
            return jsonView().addObject("fail", e.getMessage());
        }
    }


}
