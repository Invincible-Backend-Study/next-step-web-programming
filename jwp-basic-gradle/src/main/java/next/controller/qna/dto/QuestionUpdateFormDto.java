package next.controller.qna.dto;

import next.model.Question;

public class QuestionUpdateFormDto {
    private final Long questionId;
    private final String writer;
    private final String title;
    private final String contents;

    public QuestionUpdateFormDto(final Long questionId, final String writer, final String title,
                                 final String contents) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public static QuestionUpdateFormDto from(final Question question) {
        return new QuestionUpdateFormDto(
                question.getQuestionId(),
                question.getWriter(),
                question.getTitle(),
                question.getContents()
        );
    }

    public static QuestionUpdateFormDto createRequestDto(final String questionId, final String writer,
                                                         final String title,
                                                         final String contents) {
        return new QuestionUpdateFormDto(Long.parseLong(questionId), writer, title, contents);
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

    public Question toModel() {
        return new Question(questionId, null, title, contents, null, 0);
    }

}
