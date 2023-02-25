package next.controller.answer;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.dao.AnswerDao;
import next.model.Result;
import next.qna.dao.QuestionDao;


@Slf4j
public class GetQuestionController extends AbstractController {
    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var data = request.getParameterMap().entrySet().stream().map(stringEntry -> String.format("key %s value %s", stringEntry.getKey(),
                Arrays.toString(stringEntry.getValue()))).collect(Collectors.joining("\n"));
        final var id = request.getParameter("id");
        log.info(">>>> {}", data);

        Long questionId = Long.parseLong(request.getParameter("questionId"));

        return this.jsonView()
                .addObject("question", questionDao.findById(questionId))
                .addObject("answers", answerDao.findAllByQuestionId(questionId))
                .addObject("result", Result.ok());
    }
}
