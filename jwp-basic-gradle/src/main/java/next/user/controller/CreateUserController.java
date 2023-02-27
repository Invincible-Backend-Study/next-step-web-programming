package next.user.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.user.payload.request.CreateUserDto;
import next.user.service.CreateUserService;

@Slf4j
public class CreateUserController extends AbstractController {
    private final CreateUserService createUserService = new CreateUserService();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        createUserService.execute(this.convertHttpRequestToDto(request));
        return this.jspView("redirect: /user/list");
    }

    private CreateUserDto convertHttpRequestToDto(final HttpServletRequest request) {
        return new CreateUserDto(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
    }
}
