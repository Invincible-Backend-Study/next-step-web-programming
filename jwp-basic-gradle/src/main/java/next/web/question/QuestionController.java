package next.web.question;

import next.dao.QuestionDao;
import next.model.Question;
import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        QuestionDao dao = new QuestionDao();
        Question qs = dao.findByQuestionId(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("question",qs);
        return "/qna/show.jsp";
    }
}
