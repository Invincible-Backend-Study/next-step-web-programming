package next.model;

import java.sql.Timestamp;

public class Question {
    private final int questionId;
    private final String writer;
    private final String title;
    private final String contents;
    private final Timestamp createdDate;
    private final int counteofAnwer;


    public Question(int questionId, String writer, String title, String contents, Timestamp createdDate, int counteofAnwer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.counteofAnwer = counteofAnwer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getCounteofAnwer() {
        return counteofAnwer;
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
                ", counteofAnwer=" + counteofAnwer +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }
}

