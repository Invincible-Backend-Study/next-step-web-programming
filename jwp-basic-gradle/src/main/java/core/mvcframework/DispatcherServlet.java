package core.mvcframework;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name="dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final int REDIRECT_URI = 1;

    private final RequestMapping requestMapping = new RequestMapping();
    private String prefixViewPath = "/WEB-INF/views/";
    private String suffixViewPath = ".jsp";
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void setPrefixViewPath(final String prefixViewPath) {
        this.prefixViewPath = prefixViewPath;
    }

    public void setSuffixViewPath(final String suffixViewPath) {
        this.suffixViewPath = suffixViewPath;
    }

    private void initialize(final HttpServletRequest request, final HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        initialize(request, response);
        Controller handler = requestMapping.getHandlerMapping(request.getRequestURI());
        if (handler == null) {
            throw new IllegalArgumentException("[ERROR] No URL mapping");
        }
        String executeResult = handler.execute(request, response);
        log.debug("handlerExecuteResult={}", executeResult);
        resolveExecuteResult(executeResult);
    }

    private void resolveExecuteResult(final String executeResult) throws IOException, ServletException {
        if (isRedirect(executeResult)) {
            response.sendRedirect(executeResult.split(":")[REDIRECT_URI]);
            return;
        }
        resolveView(executeResult);
    }

    private boolean isRedirect(final String executeResult) {
        return executeResult.startsWith("redirect:");
    }

    private void resolveView(final String viewName) throws ServletException, IOException {
        log.debug("resolveView={}", prefixViewPath + viewName + suffixViewPath);
        request.getRequestDispatcher(prefixViewPath + viewName + suffixViewPath).forward(request, response);
    }
}
