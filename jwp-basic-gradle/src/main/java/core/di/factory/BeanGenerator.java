package core.di.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BeanGenerator {
    private final Class<?> clazz;

    public Object doGenerate(BeanFactory beanFactory) {
        if (beanFactory.getBean(clazz) != null) {
            return beanFactory.getBean(clazz);
        }
        final var constructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (constructor == null) {
            try {
                // inject가 없는 경우 기본생성자를 통해서 생성되도록 함
                return clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        final var parameters = constructor.getParameterTypes();

        final var instanceAble = Arrays.stream(parameters).noneMatch(parameter -> beanFactory.getBean(parameter) == null);
        if (instanceAble) {
            try {
                return constructor.newInstance(Arrays.stream(parameters).map(beanFactory::getBean).toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        }
        return null;
    }

}
