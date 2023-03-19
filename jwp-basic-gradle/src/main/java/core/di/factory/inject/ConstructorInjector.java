package core.di.factory.inject;

import com.google.common.collect.Sets;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ConstructorInjector extends AbstractInjector {
    private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);

    public ConstructorInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
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
