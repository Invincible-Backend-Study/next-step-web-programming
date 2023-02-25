package next.controller.user;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;

public class UpdateUserFormController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return jspView("redirect:/users/loginForm");
        }
        if (!userId.equals(user.getUserId())) {
            throw new IllegalArgumentException("[ERROR] 다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return jspView("user/update");
    }
}
