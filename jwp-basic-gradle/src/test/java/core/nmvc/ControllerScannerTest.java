package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

public class ControllerScannerTest {
//    ControllerScanner controllerScanner = new ControllerScanner();

    Map<Class<?>, Object> controllers = Maps.newHashMap();
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();


    @BeforeEach
    @SuppressWarnings("unchecked")
    void test() {
        Reflections reflections = new Reflections("next.controller.qna", "next.controller.user");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        System.out.println(annotated);
        annotated.forEach(clazz -> {
            try {
                controllers.put(clazz, clazz.getConstructor().newInstance());
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
        System.out.println(controllers);
    }

    /**
     * 찾은 메소드에서 url 정보와 requestMethod 정보를 찾아 HandlerKey를 만든다.
     */
    @Test
    void test2() {
        Set<Class<?>> clazzes = controllers.keySet();
        for (Class<?> clazz : clazzes) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<Method> methods = Arrays.stream(declaredMethods)
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .collect(Collectors.toList());
            methods.forEach(method -> {
                RequestMapping declaredAnnotation = method.getDeclaredAnnotation(RequestMapping.class);
                HandlerKey handlerKey = new HandlerKey(declaredAnnotation.value(), declaredAnnotation.method());
                HandlerExecution handlerExecution = new HandlerExecution(method);
                handlerExecutions.put(handlerKey, handlerExecution);
            });

        }

        System.out.println(handlerExecutions);
    }
}
