package next.controller;

import core.db.DataBase;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.UserDao;
import next.dao.UserDaoFactory;
import next.utils.UserUtils;


@Slf4j
public class ListUserController implements Controller{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
      /*  if(!UserUtils.isLoggedIn(request.getSession())){
            return "redirect: /user/loginForm";
        }*/
        UserDao userDao = UserDaoFactory.getUserDao();
        try {
            request.setAttribute("users", userDao.findAll());
        }catch (SQLException exception){
            request.setAttribute("users", List.of());
            log.error("{}",exception);
        }
        return "/WEB-INF/user/list.jsp";
    }
}
