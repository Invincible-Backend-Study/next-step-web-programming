package next.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.awt.geom.QuadCurve2D;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.QuestionDao;
import next.model.Question;

public class CreateQnaActionController extends AbstractController {
    private final QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        final var question = new Question(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );

        questionDao.save(question);
        return this.jspView("redirect: /");
    }
}
