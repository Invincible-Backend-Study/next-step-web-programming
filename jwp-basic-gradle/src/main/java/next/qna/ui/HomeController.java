package next.user.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.common.utils.UserUtils;
import next.qna.dao.QuestionDao;


@Slf4j
public class HomeController extends AbstractController {
    private final QuestionDao questionDao = QuestionDao.getInstance();

    public HomeController() {
        log.info("home controller initialized");
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("questions", questionDao.findAll());
        request.setAttribute("isLoggedIn", UserUtils.isLoggedIn(request.getSession()));
        return this.jspView("/WEB-INF/index.jsp");
    }
}
