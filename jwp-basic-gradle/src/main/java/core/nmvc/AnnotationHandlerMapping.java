package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.di.factory.AnnotationConfigApplicationContext;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(basePackage);
        Map<Class<?>, Object> controllers = getControllers(ac);
        controllers.keySet().forEach(clazz -> addHandlerExecutions(clazz, controllers.get(clazz)));

        logger.debug("HandlerExecutions: {}", handlerExecutions.toString());
    }

    private Map<Class<?>, Object> getControllers(AnnotationConfigApplicationContext ac) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : ac.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    private void addHandlerExecutions(Class<?> controllerClass, Object instance) {
        Set<Method> methods = ReflectionUtils.getAllMethods(controllerClass,
                ReflectionUtils.withAnnotation(RequestMapping.class));

        for (Method method : methods) {
            HandlerKey handlerKey = createHandlerKey(method.getAnnotation(RequestMapping.class));
            HandlerExecution handlerExecution = createHandlerExecution(instance, method);  // instance 화된 controller class 를 인자로 전달해야한다.
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private HandlerExecution createHandlerExecution(Object instance, Method method) {
        return new HandlerExecution(instance, method);
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
