package core.mvcframework;

import com.google.common.collect.Lists;
import core.mvcframework.controller.Controller;
import core.mvcframework.view.View;
import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings = Lists.newArrayList();

    @Override
    public void init() {
        handlerMappings.add(new LegacyHandlerMapping());
        handlerMappings.add(new AnnotationHandlerMapping("next"));
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelAndView = getHandlerResult(request, response);
        View view = modelAndView.getView();
        log.debug("executeResultView={}", view);
        view.render(modelAndView.getModel(), request, response);
    }

    private ModelAndView getHandlerResult(final HttpServletRequest request, final HttpServletResponse response) {
        Object handler = getHandler(request);
        if (handler instanceof Controller) {
            return ((Controller) handler).execute(request, response);
        }
        if (handler instanceof HandlerExecution) {
            return ((HandlerExecution) handler).handle(request, response);
        }
        throw new IllegalArgumentException("Can not find execute handler");
    }

    private Object getHandler(final HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No URL Mapping"));
    }

}
