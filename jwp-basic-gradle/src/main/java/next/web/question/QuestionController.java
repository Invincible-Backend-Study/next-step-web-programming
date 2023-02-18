package next.web.question;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class QuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        QuestionDao qsDao = new QuestionDao();
        AnswerDao asDao = new AnswerDao();
        int qsId = Integer.parseInt(req.getParameter("id"));
        Question qs = qsDao.findByQuestionId(qsId);
        List<Answer> answers = asDao.findAllByQuestonId(qsId);
        req.setAttribute("question",qs);
        req.setAttribute("answers",answers);
        return "/qna/show.jsp";
    }
}
