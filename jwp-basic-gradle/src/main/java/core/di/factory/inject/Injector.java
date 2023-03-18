package core.di.factory.inject;

import java.lang.reflect.InvocationTargetException;

public interface Injector {
    void inject(Class<?> clazz) throws InvocationTargetException, IllegalAccessException;
}
