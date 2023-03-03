package core.rc;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;


@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... args) {
        this.basePackage = args;
    }

    public void initialize() {
        final var controllerScanner = new ControllerScanner(basePackage);
        final var controllers = controllerScanner.getControllers();
        final var methods = getRequestMappingMethods(controllers.keySet());

        for (final var method : methods) {
            final var requestMapping = method.getAnnotation(RequestMapping.class);
            final var handlerKey = createHandlerKey(requestMapping);
            final var handlerExecution = new HandlerExecution(controllers.get(method.getDeclaringClass()), method);
            // 별도로 추가한 기능
            if (handlerExecutions.containsKey(handlerKey)) {
                log.error("중복된 키가 등록 되었습니다. url {} method {}", requestMapping.value(), method);
            }

            handlerExecutions.put(handlerKey, handlerExecution);

            log.debug("register handlerExecution : url is {}, method is {}", requestMapping.value(), method);
        }
    }

    private HandlerKey createHandlerKey(RequestMapping requestMapping) {
        return new HandlerKey(requestMapping.value(), requestMapping.method());
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        final Set<Method> requestMappingMethods = Sets.newHashSet();
        for (Class<?> clazz : controllers) {
            requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return requestMappingMethods;
    }


    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        RequestMethod requestMapping = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUrl, requestMapping));
    }
}
