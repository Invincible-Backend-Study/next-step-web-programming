package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;
import static core.di.BeanFactoryUtils.getInjectedConstructor;

import core.di.BeanFactory;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.BeanUtils;

public abstract class AbstractInjector implements Injector {

    private final BeanFactory beanFactory;

    public AbstractInjector(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(final Class<?> clazz) {
        Class<?> concreteClass = findConcreteClass(clazz, beanFactory.getBeanClasses()).get();
        if (Objects.isNull(beanFactory.getBean(concreteClass))) {
            instantiateClass(concreteClass);
        }
        Set<?> injectedBeans = getInjectedBeans(concreteClass);
        for (Object injectedBean : injectedBeans) {
            Class<?> beanClazz = getBeanClass(injectedBean);
            Class<?> concreteBeanClazz = findConcreteClass(beanClazz, beanFactory.getBeanClasses()).get();
            Object bean = beanFactory.getBean(concreteBeanClazz);
            if (bean == null) {
                bean = instantiateClass(concreteBeanClazz);
            }
            inject(injectedBean, bean, beanFactory);
        }
    }

    protected abstract Set<?> getInjectedBeans(final Class<?> clazz);

    protected abstract Class<?> getBeanClass(final Object injectedBean);

    protected abstract void inject(final Object injectedBean, final Object bean, final BeanFactory beanFactory);

    protected Object instantiateClass(final Class<?> preInstantiatedBean) {
        Constructor<?> injectedConstructor = getInjectedConstructor(preInstantiatedBean);
        if (injectedConstructor == null) {
            Object bean = BeanUtils.instantiateClass(preInstantiatedBean);
            beanFactory.registerBean(preInstantiatedBean, bean);
            return bean;
        }
        Object bean = instantiateParameterConstructor(injectedConstructor);
        beanFactory.registerBean(preInstantiatedBean, bean);
        return bean;
    }

    private Object instantiateParameterConstructor(final Constructor<?> injectedConstructor) {
        Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        instantiateParameter(parameterTypes);
        return BeanUtils.instantiateClass(injectedConstructor, findBeans(parameterTypes));
    }

    private void instantiateParameter(final Class<?>[] parameterTypes) {
        for (Class<?> parameterType : parameterTypes) {
            Class<?> concreteClass = findConcreteClass(parameterType, beanFactory.getBeanClasses()).get();
            if (beanFactory.getBean(concreteClass) == null) {
                instantiateClass(concreteClass);
            }
        }
    }

    private Object[] findBeans(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(type -> findConcreteClass(type, beanFactory.getBeanClasses()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(beanFactory::getBean)
                .toArray();
    }

}
