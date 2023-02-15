package next.controller;

import core.mvcframework.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.dto.ProfileUserDto;
import next.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService = new UserService();

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        log.debug("userId={}", userId);
        if (userId == null || userId.equals("")) {
            return "redirect:/users/loginForm";
        }
        ProfileUserDto profileUser = ProfileUserDto.from(userService.findUserById(userId));
        request.getSession().setAttribute("user", profileUser);
        return "user/profile";
    }
}
