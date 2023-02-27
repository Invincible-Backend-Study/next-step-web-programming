package next.qna.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import next.qna.dao.QuestionDao;
import next.qna.payload.request.CreateQuestionRequest;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateQuestionService {
    private final QuestionDao questionDao = QuestionDao.getInstance();

    public static CreateQuestionService getInstance() {
        return CreateQuestionServiceHolder.CREATE_QUESTION_SERVICE;
    }

    public void execute(CreateQuestionRequest createQuestionRequest) {
        questionDao.save(createQuestionRequest.toEntity());
    }

    private static class CreateQuestionServiceHolder {
        private static final CreateQuestionService CREATE_QUESTION_SERVICE = new CreateQuestionService();
    }
}
