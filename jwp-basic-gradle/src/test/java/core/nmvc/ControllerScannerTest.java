package core.nmvc;

import core.annotation.Controller;
import core.web.ModelAndView;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ControllerScannerTest {
    // next.api.auth.controller, 통과할수 없음

    @Test
    void init() throws Exception {
        ControllerScanner cs = new ControllerScanner();
        Map<Class<?>, Object> controllers = cs.getControllers("next.api");
        System.out.println(controllers.toString());
        assertTrue(controllers.toString().contains("class next.api.qna.controller.QuestionApiController"));
    }

    @Test
    void 리플랙션_클래스가져오기_테스트() throws Exception {
        Reflections reflections = new Reflections("next.api");
        Set<Class<? extends ModelAndView>> annotated = reflections.getSubTypesOf(ModelAndView.class);
        System.out.println(annotated);
    }
}