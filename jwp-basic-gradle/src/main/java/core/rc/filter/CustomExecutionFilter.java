package core.rc.filter;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CustomExecutionFilter {

    public boolean runnable() {
        return true;
    }

    abstract public void doFilter(Method method, HttpServletRequest servletRequest, HttpServletResponse response);
}
