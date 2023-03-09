package core.rc.filter;


import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ExecutionFilter(order = 0)
public class FilterB extends CustomExecutionFilter {
    @Override
    public void doFilter(Method method, HttpServletRequest servletRequest, HttpServletResponse response) {
        System.out.println("test");
    }
}
