package next.dao;

import java.util.List;
import next.model.Answer;

public interface AnswerDao {
    Answer addAnswer(Answer answer);

    Answer findByAnswerId(int answerId);

    List<Answer> findAllByQuestonId(int questionId);

    void deleteAnswer(int id);
}
