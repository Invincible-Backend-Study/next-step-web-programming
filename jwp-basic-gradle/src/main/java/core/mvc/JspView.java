package core.mvc;


import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect: ";
    private final String viewName;

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        if (model != null) {
            Set<String> keys = model.keySet();
            for (String key : keys) {
                request.setAttribute(key, model.get(key));
            }
            log.info("key {}", keys);
        }

        final var requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JspView jspView = (JspView) o;
        return Objects.equals(viewName, jspView.viewName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewName);
    }
}
