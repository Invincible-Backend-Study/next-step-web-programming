package next.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String REDIRECT_PREFIX = "redirect:";
    private RequestMapping requestMapping;

    @Override
    public void init() {
        requestMapping = new RequestMapping();
        requestMapping.initMapping();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String url = req.getRequestURI();
        Controller controller = requestMapping.getController(url);
        if (controller == null) {
            return;
        }
        String responseUri = controller.execute(req, res);
        if(responseUri == null){
            return;
        }
        if (responseUri.startsWith(REDIRECT_PREFIX)) {
            res.sendRedirect(responseUri.substring(REDIRECT_PREFIX.length()));
            return;
        }
        RequestDispatcher rd = req.getRequestDispatcher(responseUri);
        rd.forward(req, res);
    }
}
