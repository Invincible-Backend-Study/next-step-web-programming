package next.web.controller.question;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.mvc.AbstractController;
import next.mvc.Controller;
import next.mvc.JspView;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class QuestionController extends AbstractController {

    private final QuestionDao qsDao = new QuestionDao();
    private final AnswerDao asDao = new AnswerDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        int qsId = Integer.parseInt(req.getParameter("id"));
        Question qs = qsDao.findByQuestionId(qsId);
        List<Answer> answers = asDao.findAllByQuestonId(qsId);
        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question",qs);
        mav.addObject("answers",answers);
        return mav;
    }
}
