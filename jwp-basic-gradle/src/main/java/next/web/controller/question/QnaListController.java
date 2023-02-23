package next.web.controller.question;

import next.dao.QuestionDao;
import next.model.Question;
import next.mvc.AbstractController;
import next.mvc.JSonview;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class QnaListController extends AbstractController {

    private final QuestionDao questionDao= new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response){
        List<Question> questions = questionDao.findAll();
        return jsonView().addObject("questions",questions);
    }
}
