package next.qna.ui;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.common.utils.UserUtils;
import next.qna.service.GetAllQuestionService;


@Slf4j
public class HomeController extends AbstractController {
    private final GetAllQuestionService getAllQuestionService = GetAllQuestionService.getInstance();

    public HomeController() {
        log.info("home controller initialized");
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("questions", getAllQuestionService.execute());
        request.setAttribute("isLoggedIn", UserUtils.isLoggedIn(request.getSession()));
        return this.jspView("/WEB-INF/index.jsp");
    }
}
