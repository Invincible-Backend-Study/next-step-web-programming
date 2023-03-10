package next.model;

import java.util.Date;

public class Answer {

    private Long answerId;
    private final String writer;
    private final String contents;
    private final Date createdDate;
    private final Long questionId;

    public Answer(final Long answerId, final String writer, final String contents, final Date createdDate,
                  final Long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public Answer(final String writer, final String contents, final Long questionId) {
        this.writer = writer;
        this.contents = contents;
        this.createdDate = new Date();
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public long getTimeFromCreatedDate() {
        return createdDate.getTime();
    }

    public Long getQuestionId() {
        return questionId;
    }

    public boolean canDelete(final User user) {
        return user.containUserId(writer);
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
