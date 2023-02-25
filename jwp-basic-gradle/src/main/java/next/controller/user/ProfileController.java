package next.controller.user;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.controller.user.dto.ProfileUserDto;
import next.model.User;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        if (!SessionUtil.isLogined(request.getSession(), "user")) {
            return jspView("redirect:/loginForm");
        }
        return jspView("user/profile").addObject("profileUser",
                ProfileUserDto.from(SessionUtil.getLoginObject(request.getSession(), "user"))
        );
    }
}
