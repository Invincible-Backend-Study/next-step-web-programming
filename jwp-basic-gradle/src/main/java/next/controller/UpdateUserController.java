package next.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import next.utils.UserUtils;

public class UpdateUserController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final var user = UserUtils.getUserBy(request);

        if(user == null){
            return "redirect: /user/list";
        }

        user.updateUserInformation(
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        return "redirect: /user/list";
    }
}
