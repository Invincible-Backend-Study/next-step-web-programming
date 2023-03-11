package next.mock;

import com.google.common.collect.Maps;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import next.dao.QuestionDao;
import next.model.Question;

public class MockQuestionDao implements QuestionDao {
    private final Map<Integer, Question> questions = Maps.newHashMap();

    @Override
    public void addQuestion(Question question) {
        questions.put(question.getQuestionId(), question);
    }

    @Override
    public Question findByQuestionId(int questionId) {
        return questions.get(questionId);
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(questions.values());
    }

    @Override
    public void decreaseAnswer(int questionId) {
        Question question = questions.get(questionId);
        Question newQuestion = new Question(questionId, question.getWriter(), question.getTitle(),
                question.getContents(), Timestamp.valueOf(LocalDateTime.now()), question.getCountOfAnswer() - 1);
        questions.put(questionId, newQuestion);
    }

    @Override
    public void increaseAnswerCount(int questionId) {
        Question question = questions.get(questionId);
        Question newQuestion = new Question(questionId, question.getWriter(), question.getTitle(),
                question.getContents(), Timestamp.valueOf(LocalDateTime.now()), question.getCountOfAnswer() + 1);
        questions.put(questionId, newQuestion);
    }

    @Override
    public void deleteAnswer(int id) {
        questions.remove(id);
    }

    @Override
    public void update(String contents, String title, String id) {
        int questionId = Integer.parseInt(id);
        Question question = questions.get(questionId);
        Question newQuestion = new Question(questionId, question.getWriter(), title, contents,
                Timestamp.valueOf(LocalDateTime.now()), question.getCountOfAnswer());
        questions.put(questionId, newQuestion);
    }
}
