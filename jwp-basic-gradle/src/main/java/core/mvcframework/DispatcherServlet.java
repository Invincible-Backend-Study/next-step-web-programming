package core.mvcframework;

import core.mvcframework.controller.Controller;
import core.mvcframework.view.View;
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

    private RequestMapping requestMapping;

    @Override
    public void init() {
        requestMapping = new RequestMapping();
        requestMapping.initMapping();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        Controller handler = (Controller) requestMapping.getHandler(request);
        if (handler == null) {
            throw new IllegalArgumentException("[ERROR] No URL mapping");
        }
        ModelAndView modelAndView = handler.execute(request, response);
        View view = modelAndView.getView();
        log.debug("executeResultView={}", view);
        view.render(modelAndView.getModel(), request, response);
    }

}
