package core.di.injector;

import com.google.common.collect.Sets;
import core.di.factory.BeanFactory;
import java.util.Set;

public class ConstructorInjector extends AbstractInjector {

    public ConstructorInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(final Class<?> clazz) {
    }

    @Override
    protected Set<?> getInjectedBeans(final Class<?> clazz) {
        return Sets.newHashSet();
    }

    @Override
    protected Class<?> getBeanClass(final Object injectedBean) {
        return null;
    }

    @Override
    protected void inject(final Object injectedBean, final Object bean, final BeanFactory beanFactory) {
    }

}
