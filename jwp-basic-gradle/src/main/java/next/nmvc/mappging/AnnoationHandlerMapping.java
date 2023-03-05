package next.nmvc.mappging;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import next.nmvc.ControllerScanner;
import next.nmvc.HandlerMapping;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnoationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnoationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnoationHandlerMapping(Object... basePackage) {
        //패키지 이름을 받아와 ControllerScanner를 이용해 @Controller 어노테이션을 탐색
        this.basePackage = basePackage;
    }

    public HandlerExecution getHandler(HttpServletRequest req) {
        String requestUri = req.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(req.getMethod().toUpperCase());
        //핸들러 가져오기
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }


    public void initialize() {
        //컨트롤러 클래스 탐색
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        //컨트롤러를 Map에 컨트롤러의 키로 클래스를 값으로 인스턴스 생성해 등록한다.
        Map<Class<?>, Object> controllers = controllerScanner.getController();
        //모든 컨트롤러 객체에서 RequestMapping 어노테이션이 적용된 메소드를 찾아 저장한다.
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());
        for (Method method : methods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
        }
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        Set<Method> requestMappingMethods = Sets.newHashSet();
        for (Class<?> clazz : controllers) {
            requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return requestMappingMethods;
    }
}
