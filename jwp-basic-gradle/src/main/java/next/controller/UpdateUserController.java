package next.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;

public class UpdateUserController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final var user = this.getUser(request);
        if(user == null){
            return "redirect: /user/list";
        }
        user.updateUserInformation(request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        return "redirect: /user/list";
    }
    private User getUser(HttpServletRequest req){
        var user = req.getSession().getAttribute("user");
        if(user == null) {
            return null;
        }
        return (User) user;
    }
}
