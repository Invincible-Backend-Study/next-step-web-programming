package core.mvcframework.mapping.annotation;

import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.di.BeanFactory;
import core.di.BeanScanner;
import core.mvcframework.mapping.HandlerMapping;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        BeanScanner scanner = new BeanScanner(basePackage);
        BeanFactory beanFactory = new BeanFactory(scanner.scan());
        beanFactory.initialize();

        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        for (Class<?> clazz : controllers.keySet()) {
            Set<Method> methods = ReflectionUtils.getAllMethods(clazz,
                    ReflectionUtils.withAnnotation(RequestMapping.class));
            putHandlerExecution(controllers.get(clazz), methods);
        }
        log.debug("handlerExecute process done");
    }

    private void putHandlerExecution(final Object handler, final Set<Method> methods) {
        for (Method method : methods) {
            HandlerKey handlerKey = createHandlerKey(method.getDeclaredAnnotation(RequestMapping.class));
            HandlerExecution handlerExecution = new HandlerExecution(handler, method);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private HandlerKey createHandlerKey(final RequestMapping declaredAnnotation) {
        return new HandlerKey(declaredAnnotation.value(), declaredAnnotation.method());
    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.debug("requestUri={}", requestUri);
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, requestMethod));
    }

}