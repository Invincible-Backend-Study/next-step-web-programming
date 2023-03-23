package core.di.factory;

import com.google.common.collect.Sets;
import core.annotation.Component;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;

public class ClasspathBeanDefinitionScanner {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public ClasspathBeanDefinitionScanner(final BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @SuppressWarnings("unchecked")
    public void doScan(final Object... basePackages) {
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Repository.class, Service.class,
                Controller.class, Component.class);
        for (Class<?> beanClass : beanClasses) {
            beanDefinitionRegistry.registerBeanDefinition(beanClass, new BeanDefinition(beanClass));
        }
    }

    private Set<Class<?>> getTypesAnnotatedWith(final Reflections reflections,
                                                final Class<? extends Annotation>... annotations) {
        Set<Class<?>> annotatedClasses = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return annotatedClasses;
    }

}

