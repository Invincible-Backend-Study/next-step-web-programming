package next.web.controller.question;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.mvc.AbstractController;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import next.service.QuestionService;

public class DeleteQuestion extends AbstractController {


    private final QuestionService questionService = new QuestionService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Question question = questionService.findByQuestionId(Integer.parseInt(req.getParameter("questionId")));
        if (question == null) {
            return jsonView().addObject("fail", "해당 게시물이 존재하지 않습니다.");
        }

        if (user == null) {
            return jsonView().addObject("fail", "삭제하려면 로그인 하셔야합니다.");
        }

        if (!Objects.equals(user.getName(), question.getWriter())) {
            return jsonView().addObject("fail", "작성자가 아닐시 삭제 할 수 없습니다.");
        }

        boolean questionDeleteResult = questionService.deleteQuestion(user, question);
        if (questionDeleteResult) {
            return jsonView().addObject("success", "삭제 완료");
        }
        return jsonView().addObject("fail", "질문에 게시자 외의 답변이 존재합니다.");
    }


}
