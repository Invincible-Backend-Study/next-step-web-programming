package core.di.injector;

import com.google.common.collect.Sets;
import core.di.factory.BeanFactory;
import java.util.Set;

public class ConstructorInjector extends AbstractInjector {
    public ConstructorInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public Set<?> getInjectedBeans(Class<?> clazz) {
        return Sets.newHashSet();
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        return null;
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {

    }
}
