package next.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class JspView implements View {

    private String forwardUrl;

    private static final String REDIRECT_PREFIX = "redirect:";

    public JspView(String forwardUrl) {
        if (forwardUrl == null) {
            throw new NullPointerException("이동할 URl이 없습니다.");
        }
        this.forwardUrl = forwardUrl;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        model.keySet().forEach(key -> req.setAttribute(key,model.get(key)));
        if (forwardUrl.startsWith(REDIRECT_PREFIX)) {
            res.sendRedirect(forwardUrl.substring(REDIRECT_PREFIX.length()));
            return;
        }
        System.out.println(forwardUrl);
        RequestDispatcher rd = req.getRequestDispatcher(forwardUrl);
        rd.forward(req, res);
    }
}
