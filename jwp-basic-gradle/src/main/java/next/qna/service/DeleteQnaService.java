package next.qna.service;


import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import next.answer.dao.AnswerDao;
import next.answer.model.Answer;
import next.common.error.DomainExceptionCode;
import next.qna.dao.QuestionDao;

@Slf4j
@RequiredArgsConstructor
public class DeleteQnaService {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    public void execute(long questionId) {
        final var question = questionDao.findOptionalById(questionId).orElseThrow(DomainExceptionCode.DID_NOT_EXISTS_QUESTION_ID::createError);

        final var answers = answerDao.findAllByQuestionId(questionId);

        final var writer = question.getWriter();

        final var numberOfNotWrittenAnswerCount = answers.stream().filter(answer -> !answer.compareSameWriter(writer)).count();

        if (numberOfNotWrittenAnswerCount > 0) {
            throw DomainExceptionCode.DID_NOT_DELETE_QUESTION.createError(numberOfNotWrittenAnswerCount);
        }

        // 질문 삭제
        questionDao.deleteById(questionId);

        if (answers.size() > 0) {
            //답변삭제
            answerDao.deleteByIds(answers.stream().map(Answer::getQuestionId).collect(Collectors.toList()));
        }
    }

}
