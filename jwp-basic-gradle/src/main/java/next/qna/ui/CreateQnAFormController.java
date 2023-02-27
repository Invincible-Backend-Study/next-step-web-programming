package next.qna.ui;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.utils.UserUtils;

public class CreateQnAFormController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserUtils.isLoggedIn(request.getSession())) {
            return this.jspView("redirect: /");
        }

        request.setAttribute("name", UserUtils.getUserBy(request).getName());
        //request.setAttribute("isLoggedIn", UserUtils.isLoggedIn(request.getSession()));

        return this.jspView("/WEB-INF/qna/form.jsp");
    }
}
