package core.di.factory;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;

public class BeanScanner {
    private final Reflections reflection;

    public BeanScanner(Object... basePackage) {
        this.reflection = new Reflections(basePackage);
    }

    public Set<Class<?>> scan() {
        Class<? extends Annotation>[] classes = new Class[]{Controller.class, Service.class, Repository.class};
        Set<Class<?>> beans = new HashSet<>();
        for (Class<? extends Annotation> annotation : classes) {
            beans.addAll(reflection.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }
}
