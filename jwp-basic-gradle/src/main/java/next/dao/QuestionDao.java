package next.dao;

import java.util.List;
import next.model.Question;

public interface QuestionDao {

    List<Question> findAll();

    Question findById(final long questionId);

    List<Question> findAllOrderByCreatedDate();

    void insertNewQuestion(final Question question);

    void updateCountOfAnswerById(final Long questionId, final int countOfAnswer);

    int update(final Question question);

    int deleteById(final Long questionId);

}
