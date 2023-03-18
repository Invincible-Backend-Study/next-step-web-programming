package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.di.factory.inject.ConstructorInjector;
import core.di.factory.inject.FieldInjector;
import core.di.factory.inject.MethodInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.*;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    public Set<Class<?>> getPreInstanticateBeans() {
        return preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void putBean(Class<?> requiredType, Object instance) {
        beans.put(requiredType, instance);
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        beans.keySet().stream()
                .filter(clazz -> clazz.isAnnotationPresent(Controller.class))
                .forEach(clazz -> controllers.put(clazz, beans.get(clazz)));
        return controllers;
    }

    public void initialize() {
        ConstructorInjector constructorInjector = new ConstructorInjector(this);
        FieldInjector fieldInjector = new FieldInjector(this);
        MethodInjector methodInjector = new MethodInjector(this);

        for (Class<?> clazz : preInstanticateBeans) {
            logger.debug("BeanFactory class turn: {}", clazz.getName());
            fieldInjector.inject(clazz);
            constructorInjector.inject(clazz);
            methodInjector.inject(clazz);
        }
        logger.debug("BeanFactory beans: {}", beans.toString());
    }
}
