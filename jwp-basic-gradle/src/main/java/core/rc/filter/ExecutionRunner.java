package core.rc.filter;

import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecutionRunner {

    private List<CustomExecutionFilter> filterList;

    public static ExecutionRunner getInstance() {
        return ExecutionRunnerHolder.EXECUTION_RUNNER;
    }

    public void initialize(Object... basePackages) {
        filterList = new ExecutionFilterScanner(basePackages).getExecutionFilter();
    }

    public boolean execute(Method method, HttpServletRequest request, HttpServletResponse response) {
        for (final var filter : filterList) {
            if (!filter.runnable()) {
                return false;
            }
            filter.doFilter(method, request, response);
        }
        return true;
    }

    private static class ExecutionRunnerHolder {
        private static final ExecutionRunner EXECUTION_RUNNER = new ExecutionRunner();
    }
}
