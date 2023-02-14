package next.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.dao.template.DataAccessException;
import next.model.User;
import next.utils.UserUtils;


@Slf4j
public class UpdateUserController implements Controller{

    private final UserDao userDao = UserDaoFactory.getUserDao();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final var user = userDao.findByUserId(request.getParameter("userId"));
            user.updateUserInformation(
                    request.getParameter("password"),
                    request.getParameter("name"),
                    request.getParameter("email")
            );
            userDao.update(user);
        } catch (DataAccessException e) {
            log.error("{}",e);
        }

        //의도적으로 인증을 거치지 않도록 함
/*
        final var user = UserUtils.getUserBy(request);
        if(user == null){
            return "redirect: /user/loginForm";
        }
*/


        return "redirect: /user/list";
    }
}
