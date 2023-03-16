package next.nmvc.mappging;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.di.factory.BeanFactory;
import core.di.factory.BeanScanner;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import next.nmvc.HandlerMapping;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnoationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnoationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnoationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public HandlerExecution getHandler(HttpServletRequest req) {
        String requestUri = req.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(req.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }


    public void initialize() {
        //컨트롤러 클래스 탐색
        BeanFactory beanFactory = new BeanFactory(new BeanScanner(basePackage).scan());
        beanFactory.initialize();
        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());
        for (Method method : methods) {
            System.out.println(method);
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            handlerExecutions.put(createHandlerKey(rm),
                    new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
        }
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        Set<Method> requestMappingMethods = Sets.newHashSet();
        for (Class<?> clazz : controllers) {
            requestMappingMethods.addAll(
                    ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return requestMappingMethods;
    }
}
