package core.nmvc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.web.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;
    private MockHttpServletResponse response;

    @Before
    public void setup() throws Exception {
        handlerMapping = new AnnotationHandlerMapping("next.api");
        handlerMapping.initialize();
        System.out.println(handlerMapping.toString());

        response = new MockHttpServletResponse();
    }

    @Test
    public void list() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        HandlerExecution execution = handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
        assertEquals(null, response.getForwardedUrl());  // login한 유저만 접근 가능
    }

    @Test
    public void show() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/profile");
        Map<String, String> params = Map.of(
                "name", "자바지기"
        );
        HandlerExecution execution = handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
        assertEquals("/user/profile.jsp", response.getForwardedUrl());
    }

    @Test
    public void create() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        Map<String, String> params = Map.of(
                "userId", "tid",
                "password", "tpw",
                "name", "tname",
                "email", "t@email.com"
        );
        request.setParameters(params);

        HandlerExecution execution = handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
        assertEquals("/users", response.getRedirectedUrl());
    }
}