package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;
import static core.di.BeanFactoryUtils.getInjectedConstructor;

import core.di.BeanFactory;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;
import org.springframework.beans.BeanUtils;

public abstract class AbstractInjector implements Injector {

    private final BeanFactory beanFactory;

    public AbstractInjector(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object getBean(final Class<?> clazz) {
        return beanFactory.getBean(clazz);
    }

    public Set<Class<?>> getPreInstantiatedBeans() {
        return beanFactory.getPreInstantiatedBeans();
    }

    public Object instantiateClass(final Class<?> preInstantiatedBean) {
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
            Class<?> concreteClass = findConcreteClass(parameterType, beanFactory.getPreInstantiatedBeans());
            if (beanFactory.getBean(concreteClass) == null) {
                instantiateClass(concreteClass);
            }
        }
    }

    private Object[] findBeans(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(type -> findConcreteClass(type, beanFactory.getPreInstantiatedBeans()))
                .map(beanFactory::getBean)
                .toArray();
    }

}
