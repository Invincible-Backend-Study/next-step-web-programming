package core.di.injector;

import com.google.common.collect.Sets;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;


@Slf4j
@RequiredArgsConstructor
public abstract class AbstractInjector implements Injector {
    private final BeanFactory beanFactory;

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        for (final var injectedBean : getInjectedBeans(clazz)) {
            final var beanClass = getBeanClass(injectedBean);
            inject(injectedBean, instantiateClass(beanClass), beanFactory);
        }
    }

    private Object instantiateClass(Class<?> clazz) {
        if (beanFactory.getBean(clazz) != null) {
            return beanFactory.getBean(clazz);
        }
        final var instance = generateInstance(clazz, Sets.newHashSet());
        log.info("{} {}", clazz, instance);
        return instance;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        return null;
    }

    private void doInitialize(Class<?> preInstantiateBean) {
        if (beanFactory.getBean(preInstantiateBean) != null) {
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

        final var concreteClass = beanFactory.getConcreteClass(preInstantiateBean);
        final var constructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);

        //생성자가 없는 경우 (inject) 가 존재하지 않는 경우 기본 생성자를 적용함
        if (constructor == null) {
            return beanFactory.putInstanceToBeans(concreteClass, concreteClass.getConstructor().newInstance());
        }
        final var parameterTypes = Arrays.stream(constructor.getParameterTypes()).map(parameter -> {
            final var bean = beanFactory.getBean(parameter);
            if (bean != null) {
                return bean;
            }
            return generateInstance(parameter, basket);
        });

        return beanFactory.putInstanceToBeans(concreteClass, BeanUtils.instantiateClass(constructor, parameterTypes.toArray()));
    }


    abstract public Set<?> getInjectedBeans(Class<?> clazz);

    abstract Class<?> getBeanClass(Object injectedBean);

    abstract void inject(Object injectedBean, Object bean, BeanFactory beanFactory);


}
