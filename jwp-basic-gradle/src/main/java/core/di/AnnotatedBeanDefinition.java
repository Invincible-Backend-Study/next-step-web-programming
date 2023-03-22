package core.di;

import java.lang.reflect.Method;

public class AnnotatedBeanDefinition extends BeanDefinition {

    private Method method;

    public AnnotatedBeanDefinition(final Class<?> beanClazz, final Method method) {
        super(beanClazz);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

}
