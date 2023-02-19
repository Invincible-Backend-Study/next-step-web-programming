package next.web.controller.question;

import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AnswerController extends AbstractController {

    private final AnswerDao asDao = new AnswerDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            return jsonView();
        }
        Answer answer =  asDao.addAnswer(new Answer(
                user.getName(),req.getParameter("contents"),
                Integer.parseInt(req.getParameter("id"))
        ));
        return jsonView().addObject("answer",answer);
    }
}
