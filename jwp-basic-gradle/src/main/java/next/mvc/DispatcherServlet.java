package next.mvc;

import com.google.common.collect.Lists;
import next.nmvc.HandlerMapping;
import next.nmvc.mappging.AnnoationHandlerMapping;
import next.nmvc.mappging.HandlerExecution;

import javax.servlet.RequestDispatcher;
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

    private final List<HandlerMapping> mappings = Lists.newArrayList();

    @Override
    public void init() {
        RequestMapping legacyHandlerMapping = new RequestMapping();
        legacyHandlerMapping.initMapping();
        AnnoationHandlerMapping annoationHandlerMapping = new AnnoationHandlerMapping("next");
        annoationHandlerMapping.initialize();
        mappings.add(legacyHandlerMapping);
        mappings.add(annoationHandlerMapping);
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Object handler = getHandler(req);
        System.out.println(handler);
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
        for (HandlerMapping handlerMapping : mappings) {
            Object handler = handlerMapping.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (handler instanceof Controller) {
            return ((Controller) handler).execute(req, res);
        }
        return ((HandlerExecution) handler).handle(req, res);
    }
}
