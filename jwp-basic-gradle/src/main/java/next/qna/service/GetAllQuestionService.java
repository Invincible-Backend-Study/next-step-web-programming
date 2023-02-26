package next.qna.service;

import java.util.List;
import next.qna.dao.QuestionDao;
import next.qna.domain.Question;

public class GetAllQuestionService {
    private final QuestionDao questionDao = QuestionDao.getInstance();

    public static GetAllQuestionService getInstance() {
        return GetAllQuestionServiceHolder.GET_ALL_QUESTION_SERVICE;
    }

    public List<Question> execute() {
        return questionDao.findAll();
    }

    private static class GetAllQuestionServiceHolder {
        private static final GetAllQuestionService GET_ALL_QUESTION_SERVICE = new GetAllQuestionService();
    }
}
