package next.controller.user;

import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.user.dto.ProfileUserDto;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService = new UserService();

    @Override
    public JspView execute(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        log.debug("userId={}", userId);
        if (userId == null || userId.equals("")) {
            return new JspView("redirect:/users/loginForm");
        }
        ProfileUserDto profileUser = ProfileUserDto.from(userService.findUserById(userId));
        request.getSession().setAttribute("user", profileUser);
        return new JspView("user/profile");
    }
}
