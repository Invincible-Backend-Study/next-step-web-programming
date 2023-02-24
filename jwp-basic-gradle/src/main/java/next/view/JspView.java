package next.view;

import core.web.DispatcherServlet;
import core.web.View;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View {
    private static final Logger logger = LoggerFactory.getLogger(JspView.class);
    private final String uri;

    public JspView(String uri) {
        this.uri = uri;
    }

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("JspView Model: {}", model.toString());

        if (uri == null) {
            return;
        }
        if (uri.startsWith("redirect:")) {
            response.sendRedirect(uri.split(":")[1]);
            return;
        }

        Set<String> keys = model.keySet();
        keys.forEach(key -> request.setAttribute(key, model.get(key)));

        RequestDispatcher rd = request.getRequestDispatcher(uri);
        rd.forward(request, response);
    }
}
