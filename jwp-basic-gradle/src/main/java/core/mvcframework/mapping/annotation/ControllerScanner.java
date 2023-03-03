package core.mvcframework.mapping.annotation;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScanner {

    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private final Reflections reflections;

    public ControllerScanner(final Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> annotatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(annotatedControllers);
    }

    private Map<Class<?>, Object> instantiateControllers(final Set<Class<?>> annotatedControllers) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : annotatedControllers) {
            controllers.put(clazz, createInstance(clazz));
        }
        return controllers;
    }

    private Object createInstance(final Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

}

