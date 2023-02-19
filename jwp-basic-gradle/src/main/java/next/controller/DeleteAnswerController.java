package next.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Result;

public class DeleteAnswerController extends AbstractController {
    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        final var answerId = Long.parseLong(request.getParameter("answerId"));

        final var answer = answerDao.findById(answerId);
        if(answer == null){
            return this.jsonView().addObject("answer", null);
        }
        final var question = questionDao.findById(answer.getQuestionId());
        if(question == null){
            return this.jsonView().addObject("result", Result.fail("없음"));
        }

        answerDao.deleteById(question.getQuestionId(), answerId);
        final var updateQuestion = question.minusAnswerCountByOne();
        questionDao.updateQuestionCount(updateQuestion);

        return this.jsonView()
                .addObject("result",Result.ok())
                .addObject("countOfComments",  updateQuestion.getCountOfComment());
    }
}
