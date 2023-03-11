package next.web.controller.question;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.dao.QuestionDao;
import next.model.Form;
import next.model.Question;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;
import next.service.QuestionService;

public class QnaUpdateController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {

        if (Objects.equals(req.getMethod(), "GET")) {
            return jspView("update.jsp").addObject("questionId", req.getParameter("id"));
        }
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            Form form = new Form(req.getParameter("contents"), req.getParameter("title"),
                    req.getParameter("id"));
            questionService.updateQuestion(form, user);
            return jspView("redirect:/");
        } catch (Exception e) {
            return jsonView().addObject("fail", e.getMessage());
        }
    }

    private final QuestionService questionService = new QuestionService();
}
