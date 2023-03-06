package core.rc;

import core.mvc.ModelAndView;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class HandlerExecutionTest {
    @InjectMocks
    private HandlerExecution handlerExecution;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final var testController = TestController.class;
        final var method = testController.getDeclaredMethod("execute");
        this.handlerExecution = new HandlerExecution(new TestController(), method);
    }

    @Test
    void controller_에_dto_가_정상적으로_전달되는지() {
        final var result = this.handlerExecution.handle(request, response);
    }


    public class TestController {
        public TestController() {
        }

        public ModelAndView execute() {
            return null;
        }
    }


}