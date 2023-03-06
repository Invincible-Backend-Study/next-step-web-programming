package next.web.controller.question;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.Question;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.QuestionService;

public class QnaListController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        List<Question> questions = questionService.findAll();
        return jsonView().addObject("questions", questions);
    }

}
