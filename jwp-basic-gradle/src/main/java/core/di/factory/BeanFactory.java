package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.di.factory.inject.InjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BeanFactory implements BeanDefinitionRegistry {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Map<Class<?>, Object> beans = Maps.newHashMap();
    private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        beanDefinitions.put(clazz, beanDefinition);
        logger.debug("BeanFactory put: {} {}", clazz.getName(), beanDefinition.toString());
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return (T) bean;
        }

        Class<?> concreteClass = findConcreteClass(clazz);
        BeanDefinition beanDefinition = beanDefinitions.get(concreteClass);
        if (beanDefinition != null && beanDefinition instanceof AnnotatedBeanDefinition) {
            bean = createAnnotatedBean(beanDefinition);
            beans.put(clazz, bean);
            return (T) bean;
        }

        bean = inject(beanDefinition);
        beans.put(concreteClass, bean);
        return (T) bean;
    }

    private Optional<Object> createAnnotatedBean(BeanDefinition beanDefinition) {
        AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) beanDefinition;
        Method method = abd.getMethod();
        Object[] args = populateArguments(method.getParameterTypes());
        return BeanFactoryUtils.invokeMethod(method, getBean(method.getDeclaringClass()), args);
    }

    private Object[] populateArguments(Class<?>[] paramTypes) {
        List<Object> args = Lists.newArrayList();
        for (Class<?> param : paramTypes) {
            Object bean = getBean(param);
            if (bean == null) {
                throw new NullPointerException(param + "에 해당하는 Bean이 존재하지 않습니다.");
            }
            args.add(getBean(param));
        }
        return args.toArray();
    }

    private Class<?> findConcreteClass(Class<?> clazz) {
        Set<Class<?>> beanClasses = getBeanClasses();
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanClasses);
        if (!beanClasses.contains(concreteClazz)) {
            throw new IllegalStateException(clazz + "는 bean이 아니다.");
        }
        return concreteClazz;
    }

    private Object inject(BeanDefinition beanDefinition) {
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return BeanUtils.instantiate(beanDefinition.getInjectClass());
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        } else {
            return injectConstructor(beanDefinition);
        }
    }

    private Object injectConstructor(BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : constructor.getParameterTypes()) {
            args.add(getBean(clazz));
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    private Object injectFields(BeanDefinition beanDefinition) {
        Object bean = BeanUtils.instantiate(beanDefinition.getInjectClass());
        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            injectField(bean, field);
        }
        return bean;
    }

    private void injectField(Object bean, Field field) {
        try {
            field.setAccessible(true);
            field.set(bean, getBean(field.getType()));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            //
        }
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
        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
    }

    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }
}
