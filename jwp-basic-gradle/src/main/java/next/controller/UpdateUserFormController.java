package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserFormController extends AbstractController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormController.class);

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");
        if (value == null) {
            log.debug("Only logged-in users can access.");
            return "redirect:/user/login.jsp";
        }
        User user = (User)value;
        if (!user.isSameUser(request.getParameter("userId"))) {
            log.debug("자신의 계정에만 접근할 수 있습니다.");
            return "redirect:/user/list";
        }

        request.setAttribute("user", user);
        return "/user/update.jsp";
    }
}
