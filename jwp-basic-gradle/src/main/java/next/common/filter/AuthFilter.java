package next.common.filter;


import core.rc.filter.CustomExecutionFilter;
import core.rc.filter.ExecutionFilter;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import next.common.error.DomainExceptionCode;
import next.common.utils.UserUtils;


@Slf4j
@ExecutionFilter(order = -1)
public class AuthFilter extends CustomExecutionFilter {

    private boolean runnable = true;

    @Override
    public boolean runnable() {
        return runnable;
    }

    @SneakyThrows
    @Override
    public void doFilter(Method method, HttpServletRequest servletRequest, HttpServletResponse response) {

        log.info("auth filter");
        final var annotation = method.getAnnotationsByType(LoginOnly.class);

        if (annotation.length == 0) {
            runnable = true;
            return;
        }
        if (!UserUtils.isLoggedIn(servletRequest.getSession())) {
            log.info("user did not login");
            throw DomainExceptionCode.ACCESS_ONLY_AUTHENTICATION_USER.createError();
        }
    }
}
