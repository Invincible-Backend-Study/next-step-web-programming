package core.di.factory;

import com.google.common.collect.Maps;
import core.di.AnnotatedBeanDefinition;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.PostConstruct;
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
        var bean = beans.get(requiredType);
        if (bean != null) {
            return (T) bean;
        }
        var beanDefinition = beanDefinitions.get(requiredType);

        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            final var optionalBean = createAnnotatedBean(beanDefinition);
            optionalBean.ifPresent(b -> beans.put(requiredType, b));
            initialize(bean, requiredType);
            return (T) optionalBean.orElse(null);
        }

        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(requiredType, getBeanClasses());
        if (concreteClazz.isEmpty()) {
            return null;
        }

        beanDefinition = beanDefinitions.get(concreteClazz.get());
        bean = inject(beanDefinition);
        beans.put(concreteClazz.get(), bean);
        initialize(bean, concreteClazz.get());
        return (T) bean;
    }

    private Optional<Object> createAnnotatedBean(BeanDefinition beanDefinition) {
        final var annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
        final var method = annotatedBeanDefinition.getMethod();
        return BeanFactoryUtils.invokeMethod(method, getBean(method.getDeclaringClass()), populateArguments(method.getParameterTypes()));
    }

    private Object[] populateArguments(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes).map(parameterType -> {
            final var bean = getBean(parameterType);
            if (bean == null) {
                throw new NullPointerException(parameterTypes + "bean 이 존재하지 않습니다.");
            }
            return bean;
        }).toArray();
    }

    private void initialize(Object bean, Class<?> concreteClass) {
        Set<Method> initializeMethods = BeanFactoryUtils.getBeanMethods(concreteClass, PostConstruct.class);
        if (initializeMethods.isEmpty()) {
            return;
        }
        for (Method initializeMethod : initializeMethods) {
            log.debug("@PostConstruct Initialize Method : {}", initializeMethod);
            BeanFactoryUtils.invokeMethod(initializeMethod, bean,
                    populateArguments(initializeMethod.getParameterTypes()));
        }
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
