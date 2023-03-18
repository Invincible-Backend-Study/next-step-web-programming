package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;

@Controller
public class NewQnaController {

    @Inject
    private MyNewQnaService myNewQnaService;

    public NewQnaController() {
    }

    public MyNewQnaService getMyNewQnaService() {
        return myNewQnaService;
    }

    @Override
    public String toString() {
        return "NewQnaController{" +
                "myNewQnaService=" + myNewQnaService +
                '}';
    }
}
