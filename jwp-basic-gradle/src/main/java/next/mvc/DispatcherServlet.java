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
        if (handler == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 URL");
        }
        try {
            ModelAndView mav = execute(handler, req, res);
            View view = mav.getView();
            view.render(mav.getModel(), req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getHandler(HttpServletRequest req) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                handlerAdapter.handle(req, res, handler);
            }
        }
        return null;
    }
}
