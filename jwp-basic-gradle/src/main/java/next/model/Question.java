package next.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Question {
    private final int questionId;
    private final String writer;
    private final String title;
    private final String contents;
    private final Timestamp createdDate;
    private final int countOfAnswer;


    public Question(int questionId, String writer, String title, String contents, Timestamp createdDate,
                    int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(String writer, String title, String contents) {
        this(0, writer, title, contents, Timestamp.valueOf(LocalDateTime.now()), 0);
    }


    public int getQuestionId() {
        return questionId;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", counteofAnwer=" + countOfAnswer +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public void candelete(User user, List<Answer> answers) throws Exception {
        if (user == null) {
            throw new Exception("삭제하려면 로그인 하셔야합니다.");
        }

        if (user.isSameUser(writer)) {
            throw new Exception("작성자가 아닐 시 삭제할 수 없습니다.");
        }
        boolean canDelete = answers.stream().allMatch(answer -> answer.candelete(user));
        if (!canDelete) {
            throw new Exception("답변이 존재합니다.");
        }
    }
}

