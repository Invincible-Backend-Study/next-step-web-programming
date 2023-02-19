package core.mvc;


import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View{
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect: ";
    private final String viewName;

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)){
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return ;
        }

        Set<String> keys = model.keySet();
        for(String key: keys){
            request.setAttribute(key, model.get(key));
        }

        final var requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
    }
}
