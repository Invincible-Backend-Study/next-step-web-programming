package next.web.controller.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.AnswerService;

public class AnswerDeleteController extends AbstractController {

    private final AnswerService answerService;

    public AnswerDeleteController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String answerId = req.getParameter("answerId");
        if (user == null && answerId == null) {
            return jsonView();
        }
        answerService.deleteAnswer(Integer.parseInt(answerId));
        return jsonView().addObject("result", "성공");
    }
}
