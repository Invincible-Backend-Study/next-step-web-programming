package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;

@Controller
public class QnaSetterController {
    private MySetterQnaService mySetterQnaService;

    @Inject
    public void setMySetterQnaService(final MySetterQnaService mySetterQnaService) {
        this.mySetterQnaService = mySetterQnaService;
    }

    public MySetterQnaService getMySetterQnaService() {
        return mySetterQnaService;
    }

}