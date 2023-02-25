package next.controller.qna.dto;

import java.util.Date;
import next.model.Question;

public class QuestionCreateDto {
    private final String writer;
    private final String title;
    private final String contents;

    public QuestionCreateDto(final String writer, final String title, final String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        validate();
    }

    private void validate() {
        if (title.equals("") || contents.equals("")) {
            throw new IllegalArgumentException("[ERROR] 제목 및 본문 내용을 반드시 입력해야 합니다.");
        }
    }

    public Question toModel() {
        return new Question(writer, title, contents, new Date(), 0);
    }
}
