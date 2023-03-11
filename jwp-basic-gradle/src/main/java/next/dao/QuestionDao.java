package next.dao;

import java.util.List;
import next.model.Question;

public interface QuestionDao {
    void addQuestion(Question question);

    Question findByQuestionId(int questionId);

    List<Question> findAll();

    void decreaseAnswer(int questionId);

    void increaseAnswerCount(int questionId);

    void deleteAnswer(int id);

    void update(String contents, String title, String id);
}
