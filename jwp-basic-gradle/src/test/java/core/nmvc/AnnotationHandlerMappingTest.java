package core.nmvc;

import static org.assertj.core.api.Assertions.assertThat;

import core.di.factory.AnnotationConfigApplicationContext;
import core.web.mvcframework.ModelAndView;
import core.web.mvcframework.mapping.annotation.AnnotationHandlerMapping;
import core.web.mvcframework.mapping.annotation.HandlerExecution;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;
    private MockHttpServletResponse response;

    @BeforeEach
    public void setup() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        handlerMapping = new AnnotationHandlerMapping(ac);
        handlerMapping.initialize();

        response = new MockHttpServletResponse();
    }

    @Test
    public void list() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        HandlerExecution execution = (HandlerExecution) handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
        assertThat(response.getForwardedUrl()).isEqualTo("user/list.jsp");
    }

    @Test
    public void show() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/show");
        HandlerExecution execution = (HandlerExecution) handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
        assertThat(response.getForwardedUrl()).isEqualTo("user/show.jsp");
    }

    @Test
    public void create() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        HandlerExecution execution = (HandlerExecution) handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
        assertThat(response.getRedirectedUrl()).isEqualTo("/users");
    }
}