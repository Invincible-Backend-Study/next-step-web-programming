package com.jwp.outbound.user.ui;


import com.jwp.inbound.user.port.driving.FindMyInformationUseCase;
import com.jwp.inbound.user.port.driving.ListUserUseCase;
import com.jwp.inbound.user.port.driving.SignInUseCase;
import com.jwp.inbound.user.port.driving.SignUpUseCase;
import com.jwp.outbound.user.adapter.driving.request.CreateUserRequest;
import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.rc.ModelAttribute;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import next.common.error.DomainException;
import next.common.filter.LoginOnly;
import next.user.payload.request.UpdateUserRequest;

@Slf4j
@Controller
public class UserPresentation {

    private final ListUserUseCase listUserUseCase;
    private final SignUpUseCase signUpUseCase;
    private final FindMyInformationUseCase findMyInformationUseCase;
    private final SignInUseCase signInUseCase;

    @Inject
    public UserPresentation(ListUserUseCase listUserUseCase, SignUpUseCase signUpUseCase, FindMyInformationUseCase findMyInformationUseCase,
                            SignInUseCase signInUseCase) {
        this.listUserUseCase = listUserUseCase;
        this.signUpUseCase = signUpUseCase;
        this.findMyInformationUseCase = findMyInformationUseCase;
        this.signInUseCase = signInUseCase;
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ModelAndView renderAllUser() {
        return ModelAndView.renderPage("/user/list.jsp").addObject("users", listUserUseCase.execute());
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ModelAndView signup(@ModelAttribute CreateUserRequest createUserRequest) {
        try {
            signUpUseCase.execute(createUserRequest);
        } catch (DomainException domainException) {
            log.info("{}", domainException.getMessage());
            return ModelAndView.renderPage("/user/signup_failed.jsp");
        }
        return ModelAndView.redirect("/user/list");
    }

    @LoginOnly
    @RequestMapping(value = "/user/updateForm", method = RequestMethod.GET)
    public ModelAndView renderUpdateForm(@ModelAttribute("userId") String userId) {
        return ModelAndView.renderPage("/user/update.jsp").addObject("user", findMyInformationUseCase.execute(userId));
    }

    @LoginOnly
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute UpdateUserRequest updateUserRequest) {
        return ModelAndView.redirect("/user/list");
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("userId") String userId, @ModelAttribute("password") String password, HttpSession session) {
        try {
            session.setAttribute("user", signInUseCase.execute(userId, password));
            return ModelAndView.redirect("/");
        } catch (DomainException domainException) {
            return ModelAndView.renderPage("/user/login_failed.jsp");
        }
    }

    @LoginOnly
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return ModelAndView.redirect("/");
    }


}
