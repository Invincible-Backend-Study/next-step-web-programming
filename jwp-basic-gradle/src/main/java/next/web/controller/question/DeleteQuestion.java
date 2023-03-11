package next.web.controller.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.QuestionService;

public class DeleteQuestion extends AbstractController {


    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            boolean questionDeleteResult = questionService.deleteQuestion(
                    Integer.parseInt(req.getParameter("questionId")), user);
            if (questionDeleteResult) {
                return jsonView().addObject("success", "삭제 완료");
            }
            return jsonView().addObject("fail", "질문에 게시자 외의 답변이 존재합니다.");
        } catch (Exception e) {
            return jsonView().addObject("fail", e.getMessage());
        }
    }


}
