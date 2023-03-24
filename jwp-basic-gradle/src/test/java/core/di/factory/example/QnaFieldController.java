package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;

@Controller
public class QnaFieldController {

    @Inject
    private MyFieldQnaService myNewQnaService;

    public QnaFieldController() {
    }

    public MyFieldQnaService getMyNewQnaService() {
        return myNewQnaService;
    }

    @Override
    public String toString() {
        return "NewQnaController{" +
                "myNewQnaService=" + myNewQnaService +
                '}';
    }
}
