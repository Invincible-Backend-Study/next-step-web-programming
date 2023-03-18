package core.di.factory.inject;

import core.di.factory.BeanFactory;
import java.util.Set;

public abstract class AbstractInjector {
    protected final BeanFactory beanFactory;
    protected final Set<Class<?>> preInstanticateBeans;
    public AbstractInjector(BeanFactory beanFactory, Set<Class<?>> preInstanticateBeans) {
        this.beanFactory = beanFactory;
        this.preInstanticateBeans = preInstanticateBeans;
    }
    public void inject(Class<?> clazz) {
        if (beanFactory.getBean(clazz) == null) {
            Object object = instantiate(clazz);
            beanFactory.putBean(clazz, object);
        }
    }

    Object instantiate(Class<?> clazz) {
        return null;
    }
}
