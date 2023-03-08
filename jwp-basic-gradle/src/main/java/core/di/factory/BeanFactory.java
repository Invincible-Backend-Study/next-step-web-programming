package core.di.factory;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private final Set<Class<?>> preInstantiateBeans;

    private final Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        log.info("{}", requiredType);
        return (T) beans.get(requiredType);
    }

    /**
     * 파싱된 빈들을 가지고 인스턴스화 시켜줌
     */
    public void initialize() {
        boolean retryFlag = false;
        for (final Class<?> preInstantiateBean : preInstantiateBeans) {
            final var instance = new BeanGenerator(preInstantiateBean).doGenerate(this);
            if (instance == null) {
                retryFlag = true;
                continue;
            }
            Arrays.stream(preInstantiateBean.getInterfaces()).forEach(beanInterface -> beans.put(beanInterface, instance));
            beans.put(preInstantiateBean, instance);
        }
        if (retryFlag) {
            initialize();
        }
    }
}
