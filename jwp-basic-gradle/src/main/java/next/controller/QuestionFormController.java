package next.controller;

import core.web.ModelAndView;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;
import next.view.JsonView;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class QuestionFormController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionFormController.class);
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    protected ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView(new JsonView())
                    .addModel("result", Result.fail("질문 수정을 위해선 로그인이 필요합니다."));
        }

        Long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = null;
        try {
            question = questionDao.findByQuestionId(questionId);
            if (!user.getName().equals(question.getWriter())) {
                return new ModelAndView(new JsonView())
                        .addModel("result", Result.fail("자신이 작성한 질문만 수정할 수 있습니다."));
            }
            request.setAttribute("question", question);
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return new ModelAndView(new JspView("/qna/form.jsp")).addModel("question", question);
    }
}
