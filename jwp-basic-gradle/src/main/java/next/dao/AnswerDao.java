package next.dao;

import core.jdbc.JdbcTemplete;
import next.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDao {

    private final JdbcTemplete templete = new JdbcTemplete();

    public void addAnswer(Answer answer) {
        String sql = "INSERT INTO ANSWERS ( answerId, writer, contents, createdDate, questionId) VALUES (?,?,?,?,?)";
        templete.excuteSqlUpdate(
                sql,
                answer.getAnswerId(),
                answer.getWriter(),
                answer.getContents(),
                answer.getCreatedDate(),
                answer.getQuestionId());
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
