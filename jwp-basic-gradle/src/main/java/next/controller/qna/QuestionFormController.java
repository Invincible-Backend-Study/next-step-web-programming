package next.controller.qna;

import core.mvcframework.ModelAndView;
import core.mvcframework.controller.Controller;
import core.mvcframework.view.JspView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.utils.SessionUtil;

public class QuestionFormController implements Controller {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (SessionUtil.isLogined(session, "user")) {
            return new ModelAndView(new JspView("qna/form"));
        }
        return new ModelAndView(new JspView("redirect:/users/loginForm"));
    }
}
