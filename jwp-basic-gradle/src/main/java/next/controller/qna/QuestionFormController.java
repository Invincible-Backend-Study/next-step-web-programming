package next.controller.qna;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;

public class QuestionFormController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (isLogin(session)) {
            return "qna/form";
        }
        return "redirect:/users/loginForm";
    }

    private boolean isLogin(final HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null;
    }
}
