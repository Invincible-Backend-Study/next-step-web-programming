package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    public Map<Class<?>, Object> getControllers(Object... basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);

        return controllersWithNewInstance(annotated);
    }

    private Map<Class<?>, Object> controllersWithNewInstance(Set<Class<?>> annotated) {
        Map<Class<?>, Object> controllers = new HashMap<>();
        try {
            for (Class clazz : annotated) {
                Object object = clazz.newInstance();
                controllers.put(clazz, object);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            //
        }
        return controllers;
    }
}
