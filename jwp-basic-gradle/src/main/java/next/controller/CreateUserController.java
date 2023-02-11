package next.controller;

import core.db.DataBase;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.model.User;

@Slf4j
public class CreateUserController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        User user = User.of(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        UserDao userDao = UserDaoFactory.getUserDao();
        try {
            userDao.insert(user);
        }catch (SQLException exception){
            log.error(exception.getMessage());
        }
        //log.info("{}", DataBase.findAll());
        log.debug("user : {}", user);
        //DataBase.addUser(user);
        return "redirect: /user/list";
    }
}
