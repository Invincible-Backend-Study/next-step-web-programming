package next.web.question;

import next.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return "/qna/show.jsp";
    }
}
