package next.model;

import java.util.Date;

/**
 *  questionId 			bigint				auto_increment,
 *                            writer				varchar(30)			NOT NULL,
 *                            title				varchar(50)			NOT NULL,
 *                            contents			varchar(5000)		NOT NULL,
 *                            createdDate			timestamp			NOT NULL,
 *                            countOfAnswer int,
 *                            PRIMARY KEY               (questionId)
 */
public class Question {
    private final Long questionId;
    private final String title;
    private final String contents;
    private final Date createdDate;
    private final long countOfAnswer;

    public Question(final Long questionId, final String title, final String contents, final Date createdDate,
                    final long countOfAnswer) {
        this.questionId = questionId;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Long getQuestionId() {
        return questionId;
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

    public long getCountOfAnswer() {
        return countOfAnswer;
    }
}
