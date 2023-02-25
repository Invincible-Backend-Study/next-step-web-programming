package next.user.controller;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.user.dao.UserDao;
import next.user.dao.UserDaoFactory;


@Slf4j
public class UpdateUserController extends AbstractController {

    private final UserDao userDao = UserDaoFactory.getUserDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final var user = userDao.findByUserId(request.getParameter("userId"));
            user.updateUserInformation(
                    request.getParameter("password"),
                    request.getParameter("name"),
                    request.getParameter("email")
            );
            userDao.update(user);
        } catch (DataAccessException e) {
            log.error("{}", e);
        }
        //의도적으로 인증을 거치지 않도록 함
/*
        final var user = UserUtils.getUserBy(request);
        if(user == null){
            return "redirect: /user/loginForm";
        }
*/

        return this.jspView("redirect: /user/list");
    }
}
