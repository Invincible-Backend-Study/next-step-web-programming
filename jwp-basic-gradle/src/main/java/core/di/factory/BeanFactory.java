package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.Controller;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class BeanFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private final Set<Class<?>> preInstantiateBeans;

    private final Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        log.info("{}", requiredType);
        return (T) beans.get(requiredType);
    }

    @SneakyThrows
    public void initialize() {
        preInstantiateBeans.forEach(this::doInitialize);
    }

    private void doInitialize(Class<?> preInstantiateBean) {
        if (beans.containsKey(preInstantiateBean)) {
            return;
        }
        final var instance = generateInstance(preInstantiateBean, Sets.newHashSet());
        log.info("{} {}", preInstantiateBean, instance);
    }

    @SneakyThrows
    private Object generateInstance(Class<?> preInstantiateBean, Set<Class<?>> basket) {
        if (basket.contains(preInstantiateBean)) {
            log.error("{} {}", basket, preInstantiateBean);
            throw new IllegalStateException("순환참조가 일어나고 있습니다.");
        }
        basket.add(preInstantiateBean);

        log.info("{}", preInstantiateBean);
        final var concreteClass = BeanFactoryUtils.findConcreteClass(preInstantiateBean, preInstantiateBeans);
        final var constructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);

        //생성자가 없는 경우 (inject) 가 존재하지 않는 경우 기본 생성자를 적용함
        if (constructor == null) {
            return putInstanceToBeans(concreteClass, concreteClass.getConstructor().newInstance());
        }
        final var parameterTypes = Arrays.stream(constructor.getParameterTypes()).map(parameter -> {
            if (beans.containsKey(parameter)) {
                return beans.get(parameter);
            }
            return generateInstance(parameter, basket);
        });

        return putInstanceToBeans(concreteClass, BeanUtils.instantiateClass(constructor, parameterTypes.toArray()));
    }

    public Object putInstanceToBeans(Class<?> clazz, Object instance) {
        Arrays.stream(clazz.getInterfaces()).forEach(clazzInterface -> beans.put(clazzInterface, instance));
        beans.put(clazz, instance);
        return instance;
    }

    @SneakyThrows
    private Object generateDefaultConstructor(Constructor<?> constructor) {
        return constructor.newInstance();
    }

    /**
     * 파싱된 빈들을 가지고 인스턴스화 시켜줌
     */
    public void initializeA() {
        boolean retryFlag = false;
        for (final Class<?> preInstantiateBean : preInstantiateBeans) {
            final var instance = new BeanGenerator(preInstantiateBean).doGenerate(this);
            if (instance == null) {
                retryFlag = true;
                continue;
            }
            Arrays.stream(preInstantiateBean.getInterfaces()).forEach(beanInterface -> beans.put(beanInterface, instance));
            beans.put(preInstantiateBean, instance);
        }
        if (retryFlag) {
            initialize();
        }
        /*for (final var clazz : preInstantiateBeans) {
            if (beans.get(clazz) == null) {
                instantiateClass(clazz);
            }
        }*/
    }

    private Object instantiateClass(Class<?> clazz) {
        final var bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }
        final var injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (injectedConstructor == null) {
            return beans.put(clazz, BeanUtils.instantiateClass(clazz));
        }
        log.debug("Constructor :{}", injectedConstructor);
        return beans.put(clazz, instantiateConstructor(injectedConstructor));
    }


    private Object instantiateConstructor(Constructor<?> constructor) {
        final var parameterTypes = constructor.getParameterTypes();
        final var args = Lists.newArrayList();
        for (final var clazz : parameterTypes) {
            final var concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
            if (!preInstantiateBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아닙니다.");
            }
            var bean = beans.get(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers() {
        final Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (final var clazz : preInstantiateBeans) {
            final var annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }
}
