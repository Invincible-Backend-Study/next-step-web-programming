package next.controller.user.legacy;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.utils.SessionUtil;

public class UpdateUserFormController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User loginedUser = SessionUtil.getLoginObject(request.getSession(), "user");
        if (!userId.equals(loginedUser.getUserId())) {
            throw new IllegalArgumentException("[ERROR] 다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return jspView("user/update");
    }

}
