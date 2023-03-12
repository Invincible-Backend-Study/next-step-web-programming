package core.web;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)  // loadOnStartup, 서블릿 요청이 있기전에 미리 초기화함 (숫자가 작을수록 먼저 초기화됨)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String API_PATH_PREFIX = "next.api";
    AnnotationHandlerMapping annotationHandlerMapping;

    @Override
    public void init() {
        annotationHandlerMapping = new AnnotationHandlerMapping(API_PATH_PREFIX);
        annotationHandlerMapping.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("DispatcherServlet: {} {}", req.getMethod(), req.getRequestURI());
        try {
            HandlerExecution handlerExecution = annotationHandlerMapping.getHandler(req);
            logger.debug("dispatcher servlet handler excuteion: {}", handlerExecution.toString());
            ModelAndView modelAndView = handlerExecution.handle(req, resp);

            modelAndView.getView().render(modelAndView.getModel(), req, resp);
        } catch (Exception e) {
            logger.error("DispatcherServlet: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
