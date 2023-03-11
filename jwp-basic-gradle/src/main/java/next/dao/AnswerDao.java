package next.dao;

import java.util.List;
import next.model.Answer;

public interface AnswerDao {

    List<Answer> findAllByQuestionId(final long questionId);

    Answer insert(final Answer answer);

    int deleteById(final long answerId);

    Answer findById(final long answerId);

    int deleteAllByQuestionId(final Long questionId);

}
