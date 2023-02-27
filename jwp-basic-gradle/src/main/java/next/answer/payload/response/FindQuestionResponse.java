package next.answer.payload.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import next.answer.model.Answer;
import next.qna.domain.Question;

@Getter
@RequiredArgsConstructor
public class FindQuestionResponse {
    private final Question question;
    private final List<Answer> answers;

    public static FindQuestionResponse of(Question question, List<Answer> answers) {
        return new FindQuestionResponse(question, answers);
    }
}
