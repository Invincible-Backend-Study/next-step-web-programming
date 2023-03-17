package core.di.factory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.di.injector.ConstructorInjector;
import core.di.injector.FieldInjector;
import core.di.injector.Injector;
import core.di.injector.SetterInjector;
import java.util.Arrays;
import java.util.List;
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

    private final List<Injector> injectors;

    public BeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;

        injectors = Arrays.asList(new FieldInjector(this), new SetterInjector(this), new ConstructorInjector(this));
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        log.info("{}", requiredType);
        return (T) beans.get(requiredType);
    }

    @SneakyThrows
    public void initialize() {

        preInstantiateBeans.forEach(clazz -> {
            if (beans.get(clazz) == null) {
                log.debug("instantiated Class:{}", clazz);
                inject(clazz);
            }
        });
        //preInstantiateBeans.forEach(this::doInitialize);
    }

    private void inject(Class<?> clazz) {
        injectors.forEach(injector -> injector.inject(clazz));
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

    public Class<?> getConcreteClass(Class<?> preInstantiateBean) {
        return BeanFactoryUtils.findConcreteClass(preInstantiateBean, preInstantiateBeans);
    }
}
