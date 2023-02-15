package next.service;

import java.util.List;
import next.dao.QuestionDao;
import next.model.Question;

public class QuestionService {
    private final QuestionDao questionDao = new QuestionDao();

    public List<Question> findAll() {
        return questionDao.findAll();
    }
}
