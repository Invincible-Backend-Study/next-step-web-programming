package core.nmvc;

public class AnnotationHandlerMappingTest {
//    private AnnotationHandlerMapping handlerMapping;
//    private MockHttpServletResponse response;
//
//    @BeforeEach
//    public void setup() {
//        handlerMapping = new AnnotationHandlerMapping("core.nmvc");
//        handlerMapping.initialize();
//
//        response = new MockHttpServletResponse();
//    }
//
//    @Test
//    public void list() throws Exception {
//        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
//        HandlerExecution execution = handlerMapping.getHandler(request);
//        ModelAndView mav = execution.handle(request, response);
//        mav.getView().render(mav.getModel(), request, response);
//        assertEquals("/users/list.jsp", response.getForwardedUrl());
//    }
//
//    @Test
//    public void show() throws Exception {
//        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/show");
//        HandlerExecution execution = handlerMapping.getHandler(request);
//        ModelAndView mav = execution.handle(request, response);
//        mav.getView().render(mav.getModel(), request, response);
//        assertEquals("/users/show.jsp", response.getForwardedUrl());
//    }
//
//    @Test
//    public void create() throws Exception {
//        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
//        HandlerExecution execution = handlerMapping.getHandler(request);
//        ModelAndView mav = execution.handle(request, response);
//        mav.getView().render(mav.getModel(), request, response);
//        assertEquals("/users", response.getRedirectedUrl());
//    }
}