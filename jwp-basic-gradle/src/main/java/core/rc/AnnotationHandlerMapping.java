package core.rc;


import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.di.factory.ApplicationContext;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;


@RequiredArgsConstructor
@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {

    private final ApplicationContext applicationContext;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public void initialize() {
        initializeController();
    }

    private void initializeController() {

        final var controllers = getControllers(applicationContext);
        final var methods = getRequestMappingMethods(controllers.keySet());

        this.handlerExecutions = methods.stream()
                .collect(Collectors.toMap(this::createHandlerKey,
                        method -> new HandlerExecution(controllers.get(method.getDeclaringClass()), method)));
    }

    private HandlerKey createHandlerKey(Method method) {
        final var requestMapping = method.getAnnotation(RequestMapping.class);
        log.debug("register handlerExecution : url is {}, method is {}", requestMapping.value(), method);
        return this.createHandlerKey(requestMapping);
    }

    private HandlerKey createHandlerKey(RequestMapping requestMapping) {
        return new HandlerKey(requestMapping.value(), requestMapping.method());
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        return controllers.stream()
                .map(clazz -> ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }


    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        RequestMethod requestMapping = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUrl, requestMapping));
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext applicationContext) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (final var clazz : applicationContext.getBeanClasses()) {
            final var annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, applicationContext.getBean(clazz));
            }
        }
        return controllers;
    }
}
