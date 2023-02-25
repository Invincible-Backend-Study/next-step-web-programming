package next.controller.answer;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.qna.dao.QuestionDao;

public class ShowController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        request.setAttribute("question", questionDao.findById(questionId));
        request.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
        return this.jspView("/WEB-INF/qna/show.jsp");
    }
}
