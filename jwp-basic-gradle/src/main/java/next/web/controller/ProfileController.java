package next.web.controller;

import core.mvcframework.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.web.controller.dto.ProfileUserDto;
import next.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService = new UserService();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        log.debug("userId={}", userId);
        if (userId == null || userId.equals("")) {
            return "redirect:/users/login";
        }
        ProfileUserDto profileUser = ProfileUserDto.from(userService.findUserById(userId));
        request.getSession().setAttribute("user", profileUser);
        return "user/profile";
    }
}
