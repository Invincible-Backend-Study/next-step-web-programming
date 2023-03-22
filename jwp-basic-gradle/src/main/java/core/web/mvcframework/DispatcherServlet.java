package core.web.mvcframework;

import com.google.common.collect.Lists;
import core.web.mvcframework.adapter.ControllerHandlerAdapter;
import core.web.mvcframework.adapter.HandlerAdapter;
import core.web.mvcframework.adapter.HandlerExecutionHandlerAdapter;
import core.web.mvcframework.mapping.HandlerMapping;
import core.web.mvcframework.view.View;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerAdapter> handlerAdapters = Lists.newArrayList();
    private final List<HandlerMapping> handlerMappings = Lists.newArrayList();
    private HandlerMapping handlerMapping;

    public DispatcherServlet(final HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void init() {
        initializeHandlerAdapters();
        handlerMappings.add(handlerMapping);
    }

    private void initializeHandlerAdapters() {
        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelAndView = getExecuteResult(request, response);
        View view = modelAndView.getView();
        log.debug("executeResultView={}", view);
        view.render(modelAndView.getModel(), request, response);
    }

    private ModelAndView getExecuteResult(final HttpServletRequest request, final HttpServletResponse response) {
        Object handler = getHandler(request);
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.support(handler))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not find execute handler"))
                .execute(request, response, handler);
    }

    private Object getHandler(final HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No URL Mapping"));
    }

}
