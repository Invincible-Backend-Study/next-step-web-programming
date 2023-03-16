package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    private Object instantiateClass(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }
        Constructor<?> injectConstructor = getConstructorsContainingInjects(clazz);
        if (injectConstructor == null) {
            bean = instantiate(clazz);
            beans.put(clazz, bean);
            return bean;
        }
        bean = instantiateConstructor(injectConstructor);
        beans.put(clazz, bean);
        return bean;
    }

    private Object instantiate(Class<?> clazz) {
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "인터페이스는 안돼요 안됑.");
        }
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<?> getConstructorsContainingInjects(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            boolean isContainInject = Arrays.stream(constructor.getAnnotations())
                    .anyMatch(annotation -> annotation.annotationType().equals(Inject.class));
            if (isContainInject) {
                return constructor;
            }
        }
        return null;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        //생성자의 파라미터 싹다 가져와!!
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);

            if (!preInstanticateBeans.contains(concreteClass)) {
                throw new IllegalStateException(clazz + "는 빈이 아니다.");
            }
            Object bean = beans.get(concreteClass);
            if (bean == null) {
                bean = instantiateClass(concreteClass);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public void initialize() {
        for (Class<?> clazz : preInstanticateBeans) {
            if (beans.get(clazz) == null) {
                instantiateClass(clazz);
            }
        }
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        for (Class<?> clazz : preInstanticateBeans) {
            Controller annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, getBean(clazz));
            }
        }
        return controllers;
    }
}
