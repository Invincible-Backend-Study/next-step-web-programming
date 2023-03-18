package core.di;

import static core.di.BeanFactoryUtils.findConcreteClass;
import static core.di.BeanFactoryUtils.getInjectedConstructor;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class BeanFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstantiatedBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstantiatedBeans) {
        this.preInstantiatedBeans = preInstantiatedBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> preInstantiatedBean : preInstantiatedBeans) {
            if (beans.containsKey(preInstantiatedBean)) {
                continue;
            }
            instantiateClass(preInstantiatedBean);
        }
        log.debug("complete bean scan");
    }

    private void instantiateClass(final Class<?> preInstantiatedBean) {
        Constructor<?> injectedConstructor = getInjectedConstructor(preInstantiatedBean);
        if (injectedConstructor == null) {
            Object bean = BeanUtils.instantiateClass(findConcreteClass(preInstantiatedBean, preInstantiatedBeans));
            beans.put(preInstantiatedBean, bean);
            return;
        }
        beans.put(preInstantiatedBean, instantiateParameterConstructor(injectedConstructor));
    }

    private Object instantiateParameterConstructor(final Constructor<?> injectedConstructor) {
        Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        instantiateParameter(parameterTypes);
        return BeanUtils.instantiateClass(injectedConstructor, findBeans(parameterTypes));
    }

    private void instantiateParameter(final Class<?>[] parameterTypes) {
        for (Class<?> parameterType : parameterTypes) {
            if (!beans.containsKey(parameterType)) {
                instantiateClass(parameterType);
            }
        }
    }

    private Object[] findBeans(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(parameterType -> beans.get(parameterType))
                .toArray();
    }

    public Map<Class<?>, Object> getControllers() {
        return preInstantiatedBeans.stream()
                .filter(preInstantiatedBean -> preInstantiatedBean.isAnnotationPresent(Controller.class))
                .collect(Collectors.toMap(preInstantiatedBean -> preInstantiatedBean,
                        preInstantiatedBean -> beans.get(preInstantiatedBean)));
    }
}
