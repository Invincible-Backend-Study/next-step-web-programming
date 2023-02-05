package next.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.model.User;


@Slf4j
public class UpdateUserFormController implements Controller{
    private User getUser(HttpServletRequest req){
        var user = req.getSession().getAttribute("user");
        if(user == null) {
            return null;
        }
        return (User) user;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final var user = this.getUser(request);
        if(user == null){
            return "redirect: /user/list";
        }
        log.info("{}", user);
        request.setAttribute("user", user);
        return "/user/update.jsp";
    }
}
