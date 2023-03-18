package core.di;

import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;

public class BeanScanner {

    private final Reflections reflections;

    public BeanScanner(final Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> scan() {
        return getAllTypesAnnotatedWith(Repository.class, Service.class, Controller.class);
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getAllTypesAnnotatedWith(final Class<? extends Annotation>... annotations) {
        Set<Class<?>> annotatedClasses = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return annotatedClasses;
    }

}

