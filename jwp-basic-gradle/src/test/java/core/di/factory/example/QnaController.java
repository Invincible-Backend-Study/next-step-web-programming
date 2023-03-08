package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.mvc.AbstractNewController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class QnaController extends AbstractNewController {
    private final MyQnaService qnaService;

    @Inject
    public QnaController(MyQnaService qnaService) {
        this.qnaService = qnaService;
    }

    public MyQnaService getQnaService() {
        return qnaService;
    }

    @RequestMapping("/questions")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("/qna/list.jsp");
    }
}
