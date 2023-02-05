package core.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = {"/css/*", "/js/*", "/fonts/*", "/images/*", "/favicon.ico"})
public class ResourceFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ResourceFilter.class);
    private RequestDispatcher defaultRequestDispatcher;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        defaultRequestDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        defaultRequestDispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
