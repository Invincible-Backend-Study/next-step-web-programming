package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;

import core.di.BeanFactory;
import core.di.BeanFactoryUtils;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetterInjector extends AbstractInjector {

    private static final Logger log = LoggerFactory.getLogger(SetterInjector.class);

    public SetterInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(final Class<?> clazz) {
        Class<?> concreteClass = findConcreteClass(clazz, getPreInstantiatedBeans());
        if (Objects.isNull(getBean(concreteClass))) {
            instantiateClass(concreteClass);
        }
        Set<Method> injectedMethods = BeanFactoryUtils.getInjectedMethods(clazz);
        for (Method injectedMethod : injectedMethods) {
            Class<?>[] parameterTypes = injectedMethod.getParameterTypes();
            if (parameterTypes.length > 1) {
                throw new IllegalArgumentException("파라미터의 개수가 1개인 메소드만 inject 할 수 있습니다.");
            }
            Class<?> concreteParameter = findConcreteClass(parameterTypes[0], getPreInstantiatedBeans());
            Object bean = getBean(concreteParameter);
            if (bean == null) {
                bean = instantiateClass(concreteParameter);
            }
            try {
                injectedMethod.invoke(getBean(concreteClass), bean);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

}
