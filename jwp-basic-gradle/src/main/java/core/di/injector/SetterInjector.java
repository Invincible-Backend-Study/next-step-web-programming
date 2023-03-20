package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;
import static core.di.BeanFactoryUtils.getInjectedConstructor;

import core.di.BeanFactory;
import core.di.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class SetterInjector implements Injector {

    private static final Logger log = LoggerFactory.getLogger(SetterInjector.class);

    private final BeanFactory beanFactory;

    public SetterInjector(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(final Class<?> clazz) {
        Class<?> concreteClass = findConcreteClass(clazz, beanFactory.getPreInstantiatedBeans());
        if (Objects.isNull(beanFactory.getBean(concreteClass))) {
            instantiateClass(concreteClass);
        }
        Set<Method> injectedMethods = BeanFactoryUtils.getInjectedMethods(clazz);
        for (Method injectedMethod : injectedMethods) {
            Class<?>[] parameterTypes = injectedMethod.getParameterTypes();
            if (parameterTypes.length > 1) {
                throw new IllegalArgumentException("파라미터의 개수가 1개인 메소드만 inject 할 수 있습니다.");
            }
            Class<?> concreteParameter = findConcreteClass(parameterTypes[0], beanFactory.getPreInstantiatedBeans());
            Object bean = beanFactory.getBean(concreteParameter);
            if (bean == null) {
                bean = instantiateClass(concreteParameter);
            }
            try {
                injectedMethod.invoke(beanFactory.getBean(concreteClass), bean);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    private Object instantiateClass(final Class<?> preInstantiatedBean) {
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
