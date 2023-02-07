package next.controller;

import core.db.DataBase;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        log.debug("updateUser : {}", user);
        DataBase.updateUserById(user);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/user/list";
    }
}
