package next.qna.domain;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@EqualsAndHashCode
@ToString
@Getter
public class Question {
    private final long questionId;

    private final String writer;

    private final String title;

    private final String contents;

    private final Date createdDate;

    private final int countOfComment;

    private Question(String writer, String title, String contents) {
        this(0, writer, title, contents, new Date(), 0);
    }

    public Question(long questionId, String writer, String title, String contents, Date createdDate,
                    int countOfComment) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfComment = countOfComment;
    }

    public Question(Long questionId, String writer, String title, String contents) {
        this(questionId, writer, title, contents, new Date(), 0);
    }

    public static Question of(String writer, String title, String contents) {
        return new Question(writer, title, contents);
    }

    public long getTimeFromCreateDate() {
        return this.createdDate.getTime();
    }

    @Override
    public String toString() {
        return "Question [questionId=" + questionId + ", writer=" + writer + ", title=" + title + ", contents="
                + contents + ", createdDate=" + createdDate + ", countOfComment=" + countOfComment + "]";
    }

    public Question plusAnswerCountByOne() {
        return new Question(
                this.questionId,
                this.writer,
                this.title,
                this.contents,
                this.createdDate,
                this.countOfComment + 1
        );
    }

    public Question minusAnswerCountByOne() {
        return new Question(
                this.questionId,
                this.writer,
                this.title,
                this.contents,
                this.createdDate,
                this.countOfComment - 1
        );
    }
}
