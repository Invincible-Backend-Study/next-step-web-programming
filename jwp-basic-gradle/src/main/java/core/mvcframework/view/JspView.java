package core.mvcframework.view;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JspView implements View {
    private static final Logger log = LoggerFactory.getLogger(JspView.class);
    private static final int REDIRECT_URI = 1;

    private final String viewName;
    private String prefixViewPath = "/WEB-INF/views/";
    private String suffixViewPath = ".jsp";

    public JspView(final String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(final Map<String, Object> model, final HttpServletRequest request,
                       final HttpServletResponse response)
            throws ServletException, IOException {
        if (viewName.startsWith("redirect:")) {
            log.debug("redirect={}", viewName);
            response.sendRedirect(viewName.split(":")[REDIRECT_URI]);
            return;
        }
        addModelToRequest(model, request);
        log.debug("renderView={}", prefixViewPath + viewName + suffixViewPath);
        request.getRequestDispatcher(prefixViewPath + viewName + suffixViewPath).forward(request, response);
    }

    private void addModelToRequest(final Map<String, Object> model, final HttpServletRequest request) {
        model.keySet().forEach(key -> request.setAttribute(key, model.get(key)));
    }
}
