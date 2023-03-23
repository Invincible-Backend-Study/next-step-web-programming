package core.di.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Method;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetterInjector extends AbstractInjector {

    private static final Logger log = LoggerFactory.getLogger(SetterInjector.class);

    public SetterInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected Set<?> getInjectedBeans(final Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    @Override
    protected Class<?> getBeanClass(final Object injectedBean) {
        Method injectedMethod = (Method) injectedBean;
        Class<?>[] parameterTypes = injectedMethod.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException("파라미터의 개수가 1개인 메소드만 inject 할 수 있습니다.");
        }
        return parameterTypes[0];
    }

    @Override
    protected void inject(final Object injectedBean, final Object bean, final BeanFactory beanFactory) {
        Method injectedMethod = (Method) injectedBean;
        try {
            injectedMethod.invoke(beanFactory.getBean(injectedMethod.getDeclaringClass()), bean);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
