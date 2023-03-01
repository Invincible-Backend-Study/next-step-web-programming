package next.web.controller.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.Answer;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.AnswerService;

public class AddAnswerController extends AbstractController {
    private final AnswerService answerService = new AnswerService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return jsonView();
        }
        Answer answer = answerService.addAnswer(new Answer(
                user.getName(), req.getParameter("contents"),
                Integer.parseInt(req.getParameter("id"))
        ));
        return jsonView().addObject("answer", answer);
    }
}
