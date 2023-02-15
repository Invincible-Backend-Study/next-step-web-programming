package next.model;

import java.time.LocalDateTime;

public class Question {
    private final long questionId;
    private final String writer;
    private final String title;
    private final String contents;
    private final int countOfAnswer;
    private final LocalDateTime createdDate;

    public Question(long questionId, String writer, String title, String contents, int countOfAnswer, LocalDateTime createdDate) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.countOfAnswer = countOfAnswer;
        this.createdDate = createdDate;
    }
}
