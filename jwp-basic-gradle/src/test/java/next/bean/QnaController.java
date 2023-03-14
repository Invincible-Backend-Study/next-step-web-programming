package next.bean;

import core.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.mvc.AbstractNewController;
import next.mvc.ModelAndView;

public class QnaController extends AbstractNewController {
    private MyQnaService myQnaService;

    public QnaController(MyQnaService qnaService) {
        this.myQnaService = qnaService;
    }

    @RequestMapping("/questions")
    public ModelAndView list(HttpServletRequest req, HttpServletResponse res) {
        return jspView("/qna/list.jsp");
    }
}
