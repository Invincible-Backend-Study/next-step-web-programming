package core.rc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;


@Slf4j
public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        final var preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(preInitiatedControllers);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        final Map<Class<?>, Object> controllers = Maps.newHashMap();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
                controllers.put(clazz, clazz.getDeclaredConstructor().newInstance());
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return controllers;
    }

    public void scan() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final var reflections = new Reflections("core.rc");

        // 컨트롤러 어노테이션을 찾음
        final var annotated = reflections.getTypesAnnotatedWith(Controller.class);

        final var mapping = new HashMap<Class<?>, Object>();
        for (var clazz : annotated) {
            final var instance = clazz.getDeclaredConstructor().newInstance();
        }
        log.info("{}", annotated);
    }
}
