package core.rc;


import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.di.factory.BeanFactory;
import core.di.factory.BeanScanner;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;


@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {

    private final Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... args) {
        this.basePackage = args;
    }

    public void initialize() {
        initializeController();
    }

    private void initializeController() {
        final var beanScanner = new BeanScanner(basePackage);
        final var beanFactory = new BeanFactory(beanScanner.scan());
        beanFactory.initialize();

        final var controllers = beanFactory.getControllers();
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
}
