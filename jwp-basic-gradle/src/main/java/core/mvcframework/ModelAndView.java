package core.mvcframework;

import core.mvcframework.view.View;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelAndView {
    private final View view;
    private final Map<String, Object> model = new HashMap<>();

    public ModelAndView(final View view) {
        this.view = view;
    }

    public void render(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        view.render(model, request, response);
    }

    public void setAttribute(final String name, final Object object) {
        model.put(name, object);
    }
}
