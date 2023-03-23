package core.di.factory.config;

import core.di.factory.InjectType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

public interface BeanDefinition {

    Constructor<?> getInjectConstructor();

    Set<Field> getInjectFields();

    Class<?> getBeanClass();

    InjectType getResolvedInjectMode();

}
