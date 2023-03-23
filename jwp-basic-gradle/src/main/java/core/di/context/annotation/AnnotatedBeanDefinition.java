package core.di.context.annotation;

import core.di.beans.factory.support.DefaultBeanDefinition;
import java.lang.reflect.Method;

public class AnnotatedBeanDefinition extends DefaultBeanDefinition {

    private Method method;

    public AnnotatedBeanDefinition(final Class<?> beanClazz, final Method method) {
        super(beanClazz);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

}
