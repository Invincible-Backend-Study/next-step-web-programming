package next.web.controller.question;

import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.Controller;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class QuestionFormController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        if(isLogined(session)){
            return jspView("redirect:/");
        }
        String contents = req.getParameter("contents");
        String title = req.getParameter("title");
        if(contents == null && title == null){
            return jspView("/qna/form.jsp");
        }
        User user = (User) session.getAttribute("user");
        Question qs = new Question(
                0,
                user.getName(),
                title,
                contents,
                Timestamp.valueOf(LocalDateTime.now()),
                0
        );
        new QuestionDao().addQuestion(qs);
        return jspView("redirect:/");
    }

    private boolean isLogined(HttpSession session) {
        return session.getAttribute("user") == null;
    }

}
