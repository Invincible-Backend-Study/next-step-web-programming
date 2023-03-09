package core.rc.filter;


import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ExecutionFilter(order = 1)
public class FilterA extends CustomExecutionFilter {
    @Override
    public void doFilter(Method method, HttpServletRequest servletRequest, HttpServletResponse response) {
    }
}
