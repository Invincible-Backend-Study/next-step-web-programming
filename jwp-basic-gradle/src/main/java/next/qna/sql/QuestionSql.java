package next.qna.sql;

public class QuestionSql {

    public static final String FindAll = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
            + "order by questionId desc";
    public static final String FIND_BY_ID = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
            + "WHERE questionId = ?";


    public static final String SAVE = "INSERT INTO QUESTIONS (writer, title, contents, createdDate) VALUES (?, ?, ?, now())";

    public static final String UPDATE_QUESTION_COUNT = "UPDATE QUESTIONS SET countOfAnswer = ? WHERE questionId = ?";
    public static final String DELETE_BY_ID = "DELETE FROM questions WHERE questionId= ?";
    public static final String UPDATE_QUESTION = "UPDATE QUESTIONS SET title = ?, contents = ? WHERE questionId = ?";
}
