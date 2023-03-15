package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MyQnaService {

    private static final Logger log = LoggerFactory.getLogger(MyQnaService.class);
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Inject
    public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
        log.debug("call qna service");
        log.debug("insert userRepository class {}", userRepository);
        log.debug("insert questionRepository class {}", questionRepository);
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }
}
