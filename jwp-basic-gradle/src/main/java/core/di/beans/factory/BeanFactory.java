package core.di.beans.factory;

import java.util.Set;

public interface BeanFactory {

    Set<Class<?>> getBeanClasses();

    <T> T getBean(final Class<T> clazz);

    void clear();
}
