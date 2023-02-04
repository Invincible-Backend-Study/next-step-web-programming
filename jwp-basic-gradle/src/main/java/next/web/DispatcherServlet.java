package next.web;

import core.web.filter.ResourceFilter;
import next.controller.Controller;
import org.apache.coyote.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)  // loadOnStartup, 서블릿 요청이 있기전에 미리 초기화함 (숫자가 작을수록 먼저 초기화됨)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("DispatcherServlet: {} {}", req.getMethod(), req.getRequestURI());
        Controller controller = RequestMapping.getController(req.getRequestURI());
        String uri = controller.execute(req, resp);

        logger.debug("DispatcherServlet: {} {}", controller.toString(), uri);
        if (uri.startsWith("redirect:")) {
            resp.sendRedirect(uri.split(":")[1]);
            return;
        }
        RequestDispatcher rd = req.getRequestDispatcher(uri);
        rd.forward(req, resp);
    }
}
