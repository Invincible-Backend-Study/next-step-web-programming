package next.dao;

import core.jdbc.JdbcTemplete;
import core.jdbc.KeyHolder;
import next.model.Answer;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnswerDao {

    private final JdbcTemplete templete = new JdbcTemplete();

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

}
