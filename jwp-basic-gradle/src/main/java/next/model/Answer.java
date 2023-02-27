package next.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Answer {
    private final int answerId;
    private final String writer;
    private final String contents;
    private final Timestamp createdDate;
    private final int questionId;

    public Answer(String writer, String contents, int questionId) {
        this(0, writer, contents, Timestamp.valueOf(LocalDateTime.now()), questionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return answerId == answer.answerId && questionId == answer.questionId && Objects.equals(writer, answer.writer) && Objects.equals(contents, answer.contents) && Objects.equals(createdDate, answer.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, writer, contents, createdDate, questionId);
    }

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
