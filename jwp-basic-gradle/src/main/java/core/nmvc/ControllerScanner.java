package core.nmvc;

import core.annotation.Controller;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

public class ControllerScanner {

    public String scanPath;
    public final List<core.mvcframework.controller.Controller> controllers = Collections.emptyList();

    public ControllerScanner(final String scanPath) {
        this.scanPath = scanPath;
    }

    @SuppressWarnings("unchecked")
    public void scanController() {
        Reflections reflections = new Reflections(scanPath);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);
        controllers.addAll((List<core.mvcframework.controller.Controller>) typesAnnotatedWith.stream()
                .map(typeClass -> {
                    try {
                        return typeClass.getConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList()));

        for (Object controller : controllers) {
            core.mvcframework.controller.Controller controllerInstance = (core.mvcframework.controller.Controller) controller;
            System.out.println(controllerInstance);
        }
    }

}

