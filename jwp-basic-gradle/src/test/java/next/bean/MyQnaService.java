package next.bean;

public class MyQnaService {
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }
}
