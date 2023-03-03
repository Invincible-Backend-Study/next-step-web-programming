package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(final Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> clazz : annotated) {
            controllers.put(clazz, instantiateController(clazz));
        }
        return controllers;
    }

    private Object instantiateController(final Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}

