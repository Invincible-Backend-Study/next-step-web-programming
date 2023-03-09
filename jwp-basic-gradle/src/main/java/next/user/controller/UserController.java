package next.user.controller;


import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.jdbc.DataAccessException;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.rc.RequestBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import next.common.filter.LoginOnly;
import next.user.dao.UserDao;
import next.user.dao.UserDaoFactory;
import next.user.entity.User;
import next.user.payload.request.CreateUserDto;
import next.user.payload.request.UpdateUserRequest;
import next.user.service.CreateUserService;
import next.user.service.FindUserService;
import next.user.service.UpdateUserService;

@Slf4j
@Controller
public class UserController {

    private final UserDao userDao = UserDaoFactory.getUserDao();
    private final CreateUserService createUserService = new CreateUserService();
    private final FindUserService findUserService = FindUserService.getInstance();
    private final UpdateUserService updateUserService = UpdateUserService.getInstance();

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ModelAndView findUser() {
        return new ModelAndView(new JspView("/WEB-INF/user/list.jsp"))
                .addObject("users", findUserService.findAll());
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ModelAndView createUser(@RequestBody CreateUserDto createUserDto) {
        createUserService.execute(createUserDto);
        return new ModelAndView(new JspView("redirect: /user/list"));
    }


    @LoginOnly
    @RequestMapping(value = "/user/updateForm", method = RequestMethod.GET)
    public ModelAndView renderUpdateForm(@RequestBody("userId") String userId, HttpServletResponse response) {
        try {
            final var user = userDao.findByUserId(userId);
            return new ModelAndView(new JspView("/WEB-INF/user/update.jsp")).addObject("user", user);
        } catch (DataAccessException e) {
            log.error("{}", e);
            return new ModelAndView(new JspView("redirect: /user/list"));
        }
    }


    @LoginOnly
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        try {
            return new ModelAndView(new JspView("/WEB-INF/user/update.jsp"))
                    .addObject("user", updateUserService.execute(updateUserRequest));
        } catch (DataAccessException e) {
            log.error("{}", e.getMessage());
            return new ModelAndView(new JspView("redirect: /user/list"));
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestBody("userId") String userId, @RequestBody("password") String password, HttpServletRequest request) {
        try {
            final User user = userDao.findByUserId(userId);
            if (user == null) {
                return new ModelAndView(new JspView("/WEB-INF/user/login_failed.jsp"));
            }
            if (user.comparePassword(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                return new ModelAndView(new JspView("redirect: /"));
            }
            return new ModelAndView(new JspView("/WEB-INF/user/login_failed.jsp"));
        } catch (DataAccessException e) {
            return new ModelAndView(new JspView("/WEB-INF/user/login_failed.jsp"));
        }
    }

    @LoginOnly
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView(new JspView("redirect: /"));
    }
}
