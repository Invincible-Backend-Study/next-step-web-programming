package next.model;

import java.util.Date;
import java.util.List;
import next.exception.CannotDeleteQuestionException;

public class Question {

    private Long questionId;
    private final String writer;
    private final String title;
    private final String contents;
    private final Date createdDate;
    private final int countOfAnswer;

    public Question(final Long questionId, final String writer, final String title, final String contents,
                    final Date createdDate, final int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(final String writer, final String title, final String contents, final Date createdDate,
                    final int countOfAnswer) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
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

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public boolean canDelete(final User user, final List<Answer> answers) {
        if (!user.containUserId(writer)) {
            throw new CannotDeleteQuestionException("다른 사용자의 글은 삭제할 수 없습니다.");
        }
        for (Answer answer : answers) {
            if (!answer.canDelete(user)) {
                throw new CannotDeleteQuestionException("질문에 다른 사용자의 답변이 달려있으므로 삭제할 수 없습니다.");
            }
        }
        return true;
    }
}
