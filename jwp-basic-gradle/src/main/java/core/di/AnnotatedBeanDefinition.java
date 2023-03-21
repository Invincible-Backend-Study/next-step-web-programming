package core.di;

import core.di.factory.BeanDefinition;
import java.lang.reflect.Method;
import lombok.Getter;


@Getter
public class AnnotatedBeanDefinition extends BeanDefinition {
    private final Method method;
    public AnnotatedBeanDefinition(Class<?> clazz, Method method) {
        super(clazz);
        this.method = method;
    }
}
