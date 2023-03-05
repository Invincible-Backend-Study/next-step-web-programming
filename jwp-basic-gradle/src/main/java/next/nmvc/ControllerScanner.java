package next.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        //가변인자로 여러개의 패키지를 가져와서 검색할 수 있음
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getController() {
        //가져온 리플렉션들에서 Controller 어노테이션을 가지고 있는 클래스만 찾아서 맵핑한다
        Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(preInitiatedControllers);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
                controllers.put(clazz, clazz.getDeclaredConstructor().newInstance());
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return controllers;
    }
}
