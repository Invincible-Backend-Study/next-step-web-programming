package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner cs = new ControllerScanner();
        Map<Class<?>, Object> controllers = cs.getControllers(basePackage);

        controllers.keySet().stream()
                .forEach(clazz -> addHandlerExecutions(clazz));
    }

    private void addHandlerExecutions(Class<?> controllerClass) {
        Set<Method> methods = ReflectionUtils.getAllMethods(controllerClass,
                ReflectionUtils.withAnnotation(RequestMapping.class));

        for (Method method : methods) {
            HandlerKey handlerKey = createHandlerKey(method.getAnnotation(RequestMapping.class));
            HandlerExecution handlerExecution = createHandlerExecution(controllerClass, method);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private HandlerExecution createHandlerExecution(Class<?> controller, Method method) {
        return new HandlerExecution(controller, method);
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());

        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }

    @Override
    public String toString() {
        return handlerExecutions.toString();
    }
}
