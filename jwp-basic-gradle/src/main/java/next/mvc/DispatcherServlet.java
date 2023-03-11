package next.mvc;

import com.google.common.collect.Lists;
import next.nmvc.HandlerMapping;
import next.nmvc.adapter.ControllerHandlerAdapter;
import next.nmvc.adapter.HandlerAdapter;
import next.nmvc.adapter.HandlerExecutionHandlerAdapter;
import next.nmvc.mappging.AnnoationHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String REDIRECT_PREFIX = "redirect:";
    private RequestMapping requestMapping;

    private final List<HandlerAdapter> handlerAdapters = Lists.newArrayList();
    private final List<HandlerMapping> handlerMappings = Lists.newArrayList();

    @Override
    public void init() {
        RequestMapping legacyHandlerMapping = new RequestMapping();
        legacyHandlerMapping.initMapping();
        AnnoationHandlerMapping annoationHandlerMapping = new AnnoationHandlerMapping("next");

        annoationHandlerMapping.initialize();

        handlerMappings.add(legacyHandlerMapping);
        handlerMappings.add(annoationHandlerMapping);

        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Object handler = getHandler(req);
        try {
            ModelAndView mav = execute(handler, req, res);
            View view = mav.getView();
            view.render(mav.getModel(), req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] URL이 존재하지 않습니다."));
    }

    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 핸들러를 찾을 수 없습니다."))
                .handle(req, res, handler);
    }
}
