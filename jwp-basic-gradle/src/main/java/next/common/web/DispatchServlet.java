package next.common.web;


import com.google.common.collect.Lists;
import core.mvc.Controller;
import core.mvc.ModelAndView;
import core.rc.AnnotationHandlerMapping;
import core.rc.HandlerExecution;
import core.rc.HandlerMapping;
import core.rc.LegacyHandlerMapping;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatchServlet.class);
    private final List<HandlerMapping> mappings = Lists.newArrayList();

    @Override
    public void init() throws ServletException {
        log.info("init dispatche servlet");

        final var legacyHandlerMapping = new LegacyHandlerMapping();
        final var annotationHandlerMapping = new AnnotationHandlerMapping(
                "next.answer.controller",
                "next.common.controller",
                "next.qna.ui",
                "next.user.controller"
        );

        annotationHandlerMapping.initialize();

        mappings.add(annotationHandlerMapping);
        mappings.add(legacyHandlerMapping);


    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Object handler = getHandler(request);
        final var requestUrl = request.getRequestURI();
        log.info("Method : {}, Request URL: {}", request.getMethod(), requestUrl);

        try {
            ModelAndView modelAndView = execute(handler, request, response);
            modelAndView.getView().render(modelAndView.getModel(), request, response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } catch (Throwable e) {
            log.error("Exception : {} ", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest request) {
        for (final var handlerMapping : mappings) {
            Object handler = handlerMapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private ModelAndView execute(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (handler instanceof Controller) {
            return ((Controller) handler).execute(request, response);
        }
        return ((HandlerExecution) handler).handle(request, response);
    }
}
