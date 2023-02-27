package next.answer.dao.sql;


import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerSql {
    public static final String INSERT = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
    public static final String FIND_BY_ID = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";
    public static final String FIND_ALL_BY_QUESTION_ID = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? order by answerId desc";
    public static final String DELETE = "DELETE FROM ANSWERS where questionId = ? and answerId = ?";

    public static String DELETE_ID_List(List<Long> ids) {
        return String.format(
                "DELETE FROM ANSWERS WHERE answerId IN ( %s )", ids.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }
}

