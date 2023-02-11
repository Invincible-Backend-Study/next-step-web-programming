package next.web.controller;

import core.mvcframework.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import next.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserFormController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormController.class);
    private final UserService userService = new UserService();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        String userId = request.getParameter("userId");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !userId.equals(user.getUserId())) {
            return "redirect:/user/login";
        }
        return "user/update";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        User updatedUser = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        userService.updateUserInformation(updatedUser);
        log.info("updatedUser={}", updatedUser);
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("user", updatedUser);
        return "redirect:/user/list";
    }
}
