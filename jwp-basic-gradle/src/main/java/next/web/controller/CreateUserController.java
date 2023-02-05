package next.web.controller;

import core.db.DataBase;
import core.web.controller.AbstractController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        return "user/form";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        log.debug("createUser={}", user);
        DataBase.addUser(user);
        return "redirect:/";
    }
}
