package next.web.user;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.UserDao;
import next.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    public String execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            req.setAttribute("users", new UserDao().findAllUser());
        } catch (SQLException err) {
            log.error(err.getMessage());
        }
        return "/user/list.jsp";
    }
}
