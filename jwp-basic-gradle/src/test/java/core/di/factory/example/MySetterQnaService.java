package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class MySetterQnaService {

    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    @Inject
    public void setQuestionRepository(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Inject
    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}