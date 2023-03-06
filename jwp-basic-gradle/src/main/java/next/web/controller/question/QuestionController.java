package next.web.controller.question;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.Answer;
import next.model.Question;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.AnswerService;
import next.service.QuestionService;

public class QuestionController extends AbstractController {


    private final QuestionService questionService = new QuestionService();
    private final AnswerService answerService = new AnswerService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        int questionId = Integer.parseInt(req.getParameter("id"));
        Question question = questionService.findByQuestionId(questionId);
        List<Answer> answers = answerService.findAllbyQuestionId(questionId);

        return jspView("/qna/show.jsp")
                .addObject("question", question)
                .addObject("answers", answers);
    }
}
