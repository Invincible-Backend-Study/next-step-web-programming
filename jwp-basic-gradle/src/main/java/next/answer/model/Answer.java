package next.answer.model;

import java.util.Date;
import java.util.Objects;

public class Answer {
    private final long answerId;

    private final String writer;

    private final String contents;

    private final Date createdDate;

    private final long questionId;

    public Answer(String writer, String contents, long questionId) {
        this(0, writer, contents, new Date(), questionId);
    }

    public Answer(long answerId, String writer, String contents, Date createdDate, long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public long getAnswerId() {
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

    public long getTimeFromCreateDate() {
        return this.createdDate.getTime();
    }

    public long getQuestionId() {
        return questionId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (answerId ^ (answerId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Answer other = (Answer) obj;
        return answerId == other.answerId;
    }

    @Override
    public String toString() {
        return "Answer [answerId=" + answerId + ", writer=" + writer + ", contents=" + contents + ", createdDate="
                + createdDate + ", questionId=" + questionId + "]";
    }

    public boolean compareSameWriter(String writer) {
        return Objects.equals(this.writer, writer);
    }
}
