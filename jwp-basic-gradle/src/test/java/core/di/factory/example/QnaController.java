package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.mvcframework.ModelAndView;
import core.mvcframework.controller.AbstractAnnotationController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class QnaController extends AbstractAnnotationController {
    private MyQnaService qnaService;

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
