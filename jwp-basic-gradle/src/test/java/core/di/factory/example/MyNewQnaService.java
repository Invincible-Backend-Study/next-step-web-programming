package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

@Service
public class MyNewQnaService {

    @Inject
    private QuestionRepository questionRepository;

    @Inject
    private UserRepository userRepository;

    public MyNewQnaService() {
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }



}
