package next.web;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.UserDao;
import next.model.User;
import next.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController implements Controller {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            User user = new User(
                    req.getParameter("userId"),
                    req.getParameter("password"),
                    req.getParameter("name"),
                    req.getParameter("email")
            );
            UserDao userDao = new UserDao();
            userDao.insert(user);
            req.setAttribute("users", new UserDao().findAll());
        } catch (IllegalStateException e) {
            log.error("공백 : { }", e);
        } catch (SQLException e){
            log.error(e.getMessage());
        }
        return "/user/list.jsp";
    }
}
