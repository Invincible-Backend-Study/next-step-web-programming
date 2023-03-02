package next.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = {"/api/*", "/users/*", "/qna/*"})
public class LoginCheckFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoginCheckFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!SessionUtil.isLogined(httpServletRequest.getSession(), "user")) {
            log.debug("needLoginUri={}", httpServletRequest.getRequestURI());
            httpServletResponse.sendRedirect("/loginForm");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
