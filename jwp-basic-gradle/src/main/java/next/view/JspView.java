package next.view;

import core.web.DispatcherServlet;
import core.web.View;
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
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (uri == null) {
            return;
        }
        if (uri.startsWith("redirect:")) {
            response.sendRedirect(uri.split(":")[1]);
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher(uri);
        rd.forward(request, response);
    }
}
