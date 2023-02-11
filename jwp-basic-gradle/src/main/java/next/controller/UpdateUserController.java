package next.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.model.User;
import next.utils.UserUtils;


@Slf4j
public class UpdateUserController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        UserDao userDao = UserDaoFactory.getUserDao();
        try {
            final var user = userDao.findByUserId(request.getParameter("userId"));
            user.updateUserInformation(
                    request.getParameter("password"),
                    request.getParameter("name"),
                    request.getParameter("email")
            );
            userDao.update(user);
        } catch (SQLException e) {
            log.error("{}",e);
        }
      /*  final var user = UserUtils.getUserBy(request);

        if(user == null){
            return "redirect: /user/list";
        }
*/


        return "redirect: /user/list";
    }
}
