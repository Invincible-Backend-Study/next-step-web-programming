package core.di.factory;

import com.google.common.collect.Maps;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class BeanFactory implements BeanDefinitionRegistry {
    private final Map<Class<?>, Object> beans = Maps.newHashMap();
    private final Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    /**
     * Bean을 생성하는 역할과 전달하는 역할을 동시에 함
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        log.info("{}", requiredType);
        final var bean = beans.get(requiredType);
        if (bean != null) {
            return (T) bean;
        }
        final var concreteClass = findConcreteClass(requiredType);
        final var beanDefinition = beanDefinitions.get(concreteClass);
        final var injectedBean = inject(beanDefinition);
        beans.put(concreteClass, injectedBean);
        return (T) injectedBean;
    }

    private Object inject(BeanDefinition beanDefinition) {
        final var beanDefinitionInjectMode = beanDefinition.getResolvedInjectMode();
        if (beanDefinitionInjectMode == InjectType.INJECT_NO) {
            return BeanUtils.instantiateClass(beanDefinition.getBeanClazz());
        }
        if (beanDefinitionInjectMode == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        }
        return injectConstructor(beanDefinition);
    }

    private Object injectConstructor(BeanDefinition beanDefinition) {
        final var constructor = beanDefinition.getInjectConstructor();
        final var args = Arrays.stream(constructor.getParameterTypes()).map(this::getBean).toArray();
        return BeanUtils.instantiateClass(constructor, args);
    }

    private Object injectFields(BeanDefinition beanDefinition) {
        final var bean = BeanUtils.instantiateClass(beanDefinition.getBeanClazz());
        final var injectFields = beanDefinition.getInjectedFields();
        for (Field field : injectFields) {
            injectFields(bean, field);
        }
        return bean;
    }

    @SneakyThrows
    private void injectFields(Object bean, Field field) {
        log.debug("Inject Bean: {}, Field : {}", bean, field);
        field.setAccessible(true);
        field.set(bean, getBean(field.getType()));
    }

    private Class<?> findConcreteClass(Class<?> requiredType) {
        final var beanClasses = getBeanClasses();
        final var concreteClazz = BeanFactoryUtils.findConcreteClass(requiredType, beanClasses);

        if (!beanClasses.contains(concreteClazz)) {
            throw new IllegalStateException(requiredType + "는 Bean이 아닙니다.");
        }
        return concreteClazz;
    }

    @SneakyThrows
    public void initialize() {
        getBeanClasses().forEach(this::getBean);
    }

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        log.debug("register bean: {}", clazz);
        beanDefinitions.put(clazz, beanDefinition);
    }

    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }

}
