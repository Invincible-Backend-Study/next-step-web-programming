package next.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.web.controller.dto.ProfileUserDto;
import next.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService = new UserService();

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
        log.debug("userId={}", userId);

        if (userId == null) {
            response.sendRedirect("/user/login");
        }
        ProfileUserDto profileUser = ProfileUserDto.from(userService.findUserById(userId));
        request.getSession().setAttribute("user", profileUser);
        request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
    }
}
