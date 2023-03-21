package core.mvcframework.mapping.annotation;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.di.ApplicationContext;
import core.mvcframework.mapping.HandlerMapping;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
        ApplicationContext applicationContext = new ApplicationContext(basePackage);

        Map<Class<?>, Object> controllers = getControllers(applicationContext);
        for (Class<?> clazz : controllers.keySet()) {
            Set<Method> methods = ReflectionUtils.getAllMethods(clazz,
                    ReflectionUtils.withAnnotation(RequestMapping.class));
            putHandlerExecution(controllers.get(clazz), methods);
        }
        log.debug("handlerExecute process done");
    }

    private Map<Class<?>, Object> getControllers(final ApplicationContext applicationContext) {
        return applicationContext.getBeanClasses()
                .stream()
                .filter(beanClass -> beanClass.isAnnotationPresent(Controller.class))
                .collect(Collectors.toMap(beanClass -> beanClass, applicationContext::getBean));
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