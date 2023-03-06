package next.common.filter;

import core.rc.filter.CustomExecutionFilter;
import core.rc.filter.ExecutionFilter;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ExecutionFilter(order = 1)
public class FilterA extends CustomExecutionFilter {
    @Override
    public void doFilter(Method method, HttpServletRequest servletRequest, HttpServletResponse response) {
        log.info("first filter");
    }
}
