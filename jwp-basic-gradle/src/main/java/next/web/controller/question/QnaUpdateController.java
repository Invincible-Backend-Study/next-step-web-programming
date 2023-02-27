package next.web.controller.question;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.Question;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.QuestionService;

public class QnaUpdateController extends AbstractController {

    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        String contents = req.getParameter("contents");
        String title = req.getParameter("title");
        String id = req.getParameter("questionId");
        if (contents == null || id == null || title == null) {
            return jspView("update.jsp").addObject("questionId", req.getParameter("id"));
        }

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return jspView("login.jsp");
        }

        Question question = questionService.findByQuestionId(Integer.parseInt(id));
        if(!Objects.equals(user.getName(), question.getWriter())){
            return jspView("redirect:/");
        }
        questionService.updateQuestion(contents,title,id);

        return jspView("redirect:/");
    }
}
