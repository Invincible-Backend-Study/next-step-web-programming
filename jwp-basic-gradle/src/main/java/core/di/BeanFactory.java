package core.di;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class BeanFactory implements BeanDefinitionRegistry {

    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    private final Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    public void initialize() {
        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
        log.debug("complete bean scan");
    }

    @Override
    public void registerBeanDefinition(final Class<?> clazz, final BeanDefinition beanDefinition) {
        log.debug("register bean = {}", clazz);
        beanDefinitions.put(clazz, beanDefinition);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(findConcreteClass(clazz));
        if (bean != null) {
            return (T) bean;
        }
        // 인젝트 작업
        Class<?> concreteClass = findConcreteClass(clazz);
        BeanDefinition beanDefinition = beanDefinitions.get(concreteClass);
        bean = inject(beanDefinition);
        beans.put(concreteClass, bean);
        return (T) bean;
    }

    private <T> Class<?> findConcreteClass(final Class<T> clazz) {
        Set<Class<?>> beanClasses = getBeanClasses();
        Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, beanClasses);
        if (!beanClasses.contains(concreteClass)) {
            throw new IllegalStateException(clazz + "는 Bean이 아닙니다.");
        }
        return concreteClass;
    }

    public Set<Class<?>> getBeanClasses() {
        return Collections.unmodifiableSet(beanDefinitions.keySet());
    }

    public Object inject(final BeanDefinition beanDefinition) {
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return BeanUtils.instantiate(beanDefinition.getBeanClass());
        }
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        }
        return injectConstructor(beanDefinition);
    }

    private Object injectFields(final BeanDefinition beanDefinition) {
        Object bean = BeanUtils.instantiate(beanDefinition.getBeanClass());
        Set<Field> fields = beanDefinition.getInjectFields();
        for (Field field : fields) {
            injectField(bean, field);
        }
        return bean;
    }

    private void injectField(final Object bean, final Field field) {
        log.debug("Inject Bean = {}, Field = {}", bean, field);
        field.setAccessible(true);
        try {
            field.set(bean, getBean(field.getType()));
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Object injectConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        List<Object> arguments = Lists.newArrayList();
        for (Class<?> parameterType : constructor.getParameterTypes()) {
            arguments.add(getBean(parameterType));
        }
        return BeanUtils.instantiateClass(constructor, arguments.toArray());
    }

    public void registerBean(final Class<?> preInstantiatedBean, final Object bean) {
        beans.put(preInstantiatedBean, bean);
    }

}
