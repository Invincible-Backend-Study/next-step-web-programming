package next.common.web;


import core.mvc.RequestMapping;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.controller.GlobalControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatchServlet.class);
    private RequestMapping requestMapping;

    @Override
    public void init() throws ServletException {
        log.info("init dispatche servlet");
        this.requestMapping = new RequestMapping();
        this.requestMapping.initMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var requestUrl = request.getRequestURI();
        log.info("Method : {}, Request URL: {}", request.getMethod(), requestUrl);

        var controller = requestMapping.findController(request.getRequestURI());

        try {
            final var abstractView = controller.execute(request, response);
            abstractView.getView().render(abstractView.getModel(), request, response);
        } catch (Exception exception) {
            final var abstractView = GlobalControllerAdvice.process(exception);
            if (abstractView == null) {
                return;
            }
            try {
                abstractView.getView().render(null, request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable e) {
            log.error("Exception : {} ", e);
            throw new ServletException(e.getMessage());
        }
    }
}

