package next.web;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.Controller;
import next.controller.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatchServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect: ";
    private RequestMapping requestMapping;

    @Override
    public void init() throws ServletException {
        log.info("init dispatche servlet");
        this.requestMapping = new RequestMapping();
        this.requestMapping.initMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var requestUrl = req.getRequestURI();
        log.info("Method : {}, Request URL: {}", req.getMethod(), requestUrl);

        var controller = requestMapping.findController(req.getRequestURI());

        try{
            var viewName = controller.execute(req,resp);
            move(viewName, req,resp);
        }catch (Throwable e){
            log.error("Exception : {} ", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)){
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return ;
        }

        var requestDispatcher = req.getRequestDispatcher(viewName);
        requestDispatcher.forward(req, resp);

    }
}

