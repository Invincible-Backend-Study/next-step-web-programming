package next.web.controller.question;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.service.AnswerService;

public class AnswerDeleteController extends AbstractController {

    private final AnswerService answerService = new AnswerService();

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
