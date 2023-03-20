package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;

import core.di.BeanFactory;

public class ConstructorInjector extends AbstractInjector {

    public ConstructorInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(final Class<?> clazz) {
        if (getBean(clazz) == null) {
            instantiateClass(findConcreteClass(clazz, getPreInstantiatedBeans()));
        }
    }

}
