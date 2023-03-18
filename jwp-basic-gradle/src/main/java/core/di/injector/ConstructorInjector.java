package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;
import static core.di.BeanFactoryUtils.getInjectedConstructor;

import core.di.BeanFactory;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import org.springframework.beans.BeanUtils;

public class ConstructorInjector implements Injector {

    private final BeanFactory beanFactory;

    public ConstructorInjector(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(final Class<?> clazz) {
        if (beanFactory.getBean(clazz) == null) {
            instantiateClass(clazz);
        }
    }

    private void instantiateClass(final Class<?> preInstantiatedBean) {
        Constructor<?> injectedConstructor = getInjectedConstructor(preInstantiatedBean);
        if (injectedConstructor == null) {
            Object bean = BeanUtils.instantiateClass(
                    findConcreteClass(preInstantiatedBean, beanFactory.getPreInstantiatedBeans()));
            beanFactory.registerBean(preInstantiatedBean, bean);
            return;
        }
        beanFactory.registerBean(preInstantiatedBean, instantiateParameterConstructor(injectedConstructor));
    }

    private Object instantiateParameterConstructor(final Constructor<?> injectedConstructor) {
        Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        instantiateParameter(parameterTypes);
        return BeanUtils.instantiateClass(injectedConstructor, findBeans(parameterTypes));
    }

    private void instantiateParameter(final Class<?>[] parameterTypes) {
        for (Class<?> parameterType : parameterTypes) {
            if (beanFactory.getBean(parameterType) == null) {
                instantiateClass(parameterType);
            }
        }
    }

    private Object[] findBeans(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(beanFactory::getBean)
                .toArray();
    }


}
