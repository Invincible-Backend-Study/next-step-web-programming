package next.mock;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import next.dao.AnswerDao;
import next.model.Answer;

public class MockAnswerDao implements AnswerDao {

    private Map<Integer, Answer> answers = Maps.newHashMap();

    @Override
    public Answer addAnswer(Answer answer) {
        return answers.put(answer.getAnswerId(), answer);
    }

    @Override
    public Answer findByAnswerId(int answerId) {
        return answers.get(answerId);
    }

    @Override
    public List<Answer> findAllByQuestonId(int questionId) {
        return answers.values().stream()
                .filter(answer -> answer.getQuestionId() == questionId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAnswer(int id) {
        answers.remove(id);
    }
}
