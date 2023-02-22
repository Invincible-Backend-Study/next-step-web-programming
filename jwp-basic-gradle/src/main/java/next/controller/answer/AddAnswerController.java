package next.controller.answer;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;


@Slf4j
public class AddAnswerController extends AbstractController{
    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        final var questionId = Long.parseLong(request.getParameter("questionId"));
        final var question = questionDao.findById(questionId);

        if(question == null){
            return this.jsonView().addObject("answer", null);
        }

        Answer answer = new Answer(request.getParameter("writer"), request.getParameter("contents"), Long.parseLong(request.getParameter("questionId")));
        log.debug("answer : {}",answer);
        Answer savedAnswer = answerDao.insert(answer);
        final var updateQuestion = question.plusAnswerCountByOne();
        questionDao.updateQuestionCount(updateQuestion);

        return this.jsonView()
                .addObject("answer", savedAnswer)
                .addObject("countOfComments",  updateQuestion.getCountOfComment());

    }
}
