package next.model;

import java.time.LocalDateTime;

public class Answer {
    private final long answerId;
    private final String writer;
    private final String title;
    private final String contents;
    private final long questionId;

    public Answer(long answerId, String writer, String title, String contents, long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.questionId = questionId;
    }
}
