package next.qna.payload.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import next.qna.domain.Question;

@Getter
@RequiredArgsConstructor
public class UpdateQuestionRequest {
    private final Long questionId;
    private final String writer;
    private final String title;
    private final String contents;

    public static UpdateQuestionRequest of(Long questionId, String writer, String title, String contents) {
        return new UpdateQuestionRequest(questionId, writer, title, contents);
    }

    public Question toEntity() {
        return new Question(questionId, writer, title, contents);
    }
}
