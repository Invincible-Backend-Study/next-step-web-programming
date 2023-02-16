package next.controller.qna.dto;

import java.util.Date;
import next.model.Answer;

public class AnswerCreateDto {
    private final String writer;
    private final String contents;
    private final Long questionId;

    public AnswerCreateDto(final String writer, final String contents, final Long questionId) {
        this.writer = writer;
        this.contents = contents;
        this.questionId = questionId;
        validate();
    }

    private void validate() {
        if (contents.equals("")) {
            throw new IllegalArgumentException("[ERROR] 본문 내용을 반드시 입력해야 합니다.");
        }
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Answer toModel() {
        return new Answer(writer, contents, new Date(), questionId);
    }
}
