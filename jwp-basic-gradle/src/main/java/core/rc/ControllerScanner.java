package core.rc;

import core.annotation.Controller;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;


@Slf4j
public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        return reflections.getTypesAnnotatedWith(Controller.class)
                .stream()
                .collect(Collectors.toMap(clazz -> clazz, this::convertClazzToInstance));
    }

    private Object convertClazzToInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
