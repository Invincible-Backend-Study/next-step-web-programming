package next.web.question;

import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuestionFormController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        if(isLogined(req)){
            return "redirect:/";
        }
        return "/qna/form.jsp";
    }

    private boolean isLogined(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return session.getAttribute("user") == null;
    }

}
