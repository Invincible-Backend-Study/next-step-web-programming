package next.api.qna.controller;

import core.web.ModelAndView;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Question;
import next.common.controller.AbstractController;
import next.common.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class ListQuestionApiController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ListQuestionApiController.class);
    private final QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Question> questions = null;
        try {
            questions = questionDao.findAll();
            request.setAttribute("questions", questions);
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return new ModelAndView(new JsonView()).addModel("questions", questions);
    }
}
