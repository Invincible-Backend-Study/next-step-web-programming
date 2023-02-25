package next.controller.qna;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.utils.SessionUtil;

public class QuestionFormController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        return jspView("redirect:/loginForm");
    }
}
