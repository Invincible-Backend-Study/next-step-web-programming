package next.model;

import java.sql.Timestamp;

public class Answer {
    private final int answerId;
    private final String writer;
    private final String contents;
    private final Timestamp createdDate;
    private final int questionId;

    public Answer(int answerId, String writer, String contents, Timestamp createdDate, int questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getContents() {
        return contents;
    }

    public String getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", questionId=" + questionId +
                '}';
    }
}
