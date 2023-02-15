package next.web.controller;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;

public class UpdateUserFormController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/loginForm";
        }
        if (!userId.equals(user.getUserId())) {
            throw new IllegalArgumentException("[ERROR] 다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return "user/update";
    }
}
