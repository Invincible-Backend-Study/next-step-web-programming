package next.answer.controller;

import next.answer.dao.AnswerDao;
import next.answer.payload.response.FindQuestionResponse;
import next.qna.dao.QuestionDao;

public class FindQuestionService {

    private final QuestionDao questionDao = QuestionDao.getInstance();
    private final AnswerDao answerDao = AnswerDao.getInstance();

    public static FindQuestionService getInstance() {
        return FindQuestionServiceHolder.FIND_QUESTION_SERVICE;
    }

    public FindQuestionResponse execute(Long questionId) {
        return FindQuestionResponse.of(questionDao.findById(questionId), answerDao.findAllByQuestionId(questionId));
    }

    private static class FindQuestionServiceHolder {
        private static final FindQuestionService FIND_QUESTION_SERVICE = new FindQuestionService();
    }
}
