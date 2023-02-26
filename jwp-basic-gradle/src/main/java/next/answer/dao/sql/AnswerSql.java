package next.dao.sql;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerSql {
    public static final String INSERT = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
    public static final String FIND_BY_ID = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";
    public static final String FIND_ALL_BY_QUESTION_ID = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? order by answerId desc";
    public static final String DELETE = "DELETE FROM ANSWERS where questionId = ? and answerId = ?";
}

