package next.dao;

import core.jdbc.JdbcTemplete;
import core.jdbc.KeyHolder;
import next.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class JdbcAnswerDao implements AnswerDao {

    private final JdbcTemplete templete = JdbcTemplete.getInstance();

    @Override
    public Answer addAnswer(Answer answer) {
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?,?,?,?)";
        KeyHolder key = new KeyHolder();
        templete.excuteSqlUpdate(
                key,
                sql,
                answer.getWriter(),
                answer.getContents(),
                answer.getCreatedDate(),
                answer.getQuestionId()
        );
        return findByAnswerId(key.getId());
    }

    @Override
    public Answer findByAnswerId(int answerId) {
        String sql = "SELECT * FROM ANSWERS WHERE answerId = ?";
        return templete.excuteFindDynamicData(sql, rs -> {
            if (rs.next()) {
                return new Answer(
                        rs.getInt("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getInt("questionId")
                );
            }
            return null;
        }, answerId);
    }

    @Override
    public List<Answer> findAllByQuestonId(int questionId) {
        String sql = "SELECT * FROM ANSWERS WHERE questionId = ?";
        return templete.excuteFindDynamicData(sql, rs -> {
            List<Answer> answers = new ArrayList<>();
            while (rs.next()) {
                answers.add(
                        new Answer(
                                rs.getInt("answerId"),
                                rs.getString("writer"),
                                rs.getString("contents"),
                                rs.getTimestamp("createdDate"),
                                rs.getInt("questionId"))
                );
            }
            return answers;
        }, questionId);
    }

    @Override
    public void deleteAnswer(int id) {
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        templete.excuteSqlUpdate(sql, id);
    }
}
