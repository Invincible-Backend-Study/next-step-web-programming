package next.web.controller.question;

import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuestionFormController extends AbstractController {

    private final QuestionDao QsDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        if(isLogined(session)){
            return jspView("redirect:/");
        }
        String contents = req.getParameter("contents");
        String title = req.getParameter("title");
        User user = (User) session.getAttribute("user");
        if(contents == null && title == null){
            return jspView("/qna/form.jsp");
        }
        QsDao.addQuestion(new Question(
                user.getName(),
                title,
                contents
        ));
        return jspView("redirect:/");
    }

    private boolean isLogined(HttpSession session) {
        return session.getAttribute("user") == null;
    }

}
