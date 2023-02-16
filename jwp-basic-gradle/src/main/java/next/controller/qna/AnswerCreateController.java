package next.controller.qna;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.controller.qna.dto.AnswerCreateDto;
import next.model.User;
import next.utils.SessionUtil;

public class AnswerCreateController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!SessionUtil.isLogin(session, "user")) {
            return "redirect:/users/loginForm";
        }
        return null;
    }


}
