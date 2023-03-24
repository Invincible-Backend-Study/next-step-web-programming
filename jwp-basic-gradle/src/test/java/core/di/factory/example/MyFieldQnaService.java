package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class MyFieldQnaService {

    @Inject
    private QuestionRepository questionRepository;

    @Inject
    private UserRepository userRepository;

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }


}
