package core.mvcframework;

import core.mvcframework.controller.Controller;
import java.io.IOException;
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

    private final RequestMapping requestMapping = new RequestMapping();

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        Controller handler = requestMapping.getHandlerMapping(request.getRequestURI());
        if (handler == null) {
            throw new IllegalArgumentException("[ERROR] No URL mapping");
        }
        ModelAndView modelAndView = handler.execute(request, response);
        log.debug("executeResultView={}", modelAndView);
        modelAndView.render(request, response);
    }
}
