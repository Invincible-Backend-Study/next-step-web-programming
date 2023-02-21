package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.web.ModelAndView;
import core.web.View;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.Result;
import next.model.User;
import next.view.JsonView;
import next.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Override
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ModelAndView(new JspView("redirect:/user/login_failed.jsp"));
            }

            Answer answer = new Answer(user.getName(),
                    request.getParameter("contents"),
                    Long.parseLong(request.getParameter("questionId")));

            answer = AnswerDao.insert(answer);
            return new ModelAndView(new JsonView()).addModel("answer", answer);
        }  catch (SQLException e) {
            log.error(e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    @Override
    protected ModelAndView doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ModelAndView(new JsonView())
                        .addModel("result", Result.fail("답변 삭제를 위해선 로그인이 필요합니다."));
            }

            Long answerId = Long.parseLong(request.getParameter("answerId"));
            Answer targetAnswer = AnswerDao.findByAnswerId(answerId);
            if (!user.getName().equals(targetAnswer.getWriter())) {
                return new ModelAndView(new JsonView())
                        .addModel("result", Result.fail("자신이 작성한 답변만 삭제할 수 있습니다."));
            }

            AnswerDao.deleteByAnswerId(answerId);
            return new ModelAndView(new JsonView()).addModel("result", Result.ok());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}