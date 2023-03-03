package next.api.qna.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.web.ModelAndView;
import next.api.qna.dao.QuestionDao;
import next.api.qna.model.Question;
import next.api.qna.service.QuestionService;
import next.common.view.JspView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ListQuestionController {
    private static final Logger log = LoggerFactory.getLogger(ListQuestionController.class);
    private final QuestionService questionService = QuestionService.getInstance();
    private final QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping(value = "/question/list")
    public ModelAndView questionList(HttpServletRequest request, HttpServletResponse response) {
        List<Question> questions = questionService.getQuestions();
        return new ModelAndView(new JspView("/qna/list.jsp")).addModel("questions", questions);
    }
}
