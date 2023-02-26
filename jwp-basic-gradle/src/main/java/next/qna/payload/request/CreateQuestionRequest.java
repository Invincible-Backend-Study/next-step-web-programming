package next.qna.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import next.qna.domain.Question;


@Getter
@RequiredArgsConstructor
public class CreateQuestionRequest {
    private final String writer;
    private final String title;
    private final String contents;

    public static CreateQuestionRequest of(String writer, String title, String contents) {
        return new CreateQuestionRequest(writer, title, contents);
    }

    public Question toEntity() {
        return Question.of(writer, title, contents);
    }
}
