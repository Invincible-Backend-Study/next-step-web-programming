package core.di;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.di.injector.ConstructorInjector;
import core.di.injector.Injector;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: BeanFactory는 빈 추가, 조회 기능만 두고 ConstructorInjector를 통해 생성자를 활용한 DI 및 인스턴스를 생성하도록 분리
public class BeanFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstantiatedBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    private List<Injector> injectors = Lists.newArrayList();

    public BeanFactory(Set<Class<?>> preInstantiatedBeans) {
        this.preInstantiatedBeans = preInstantiatedBeans;
        injectors.add(new ConstructorInjector(this));
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public Set<Class<?>> getPreInstantiatedBeans() {
        return preInstantiatedBeans;
    }

    public void initialize() {
        for (Class<?> preInstantiatedBean : preInstantiatedBeans) {
            inject(preInstantiatedBean);
        }
        log.debug("complete bean scan");
    }

    public void inject(final Class<?> clazz) {
        for (Injector injector : injectors) {
            injector.inject(clazz);
            if (beans.containsKey(clazz)) {
                break;
            }
        }
    }

    public Map<Class<?>, Object> getControllers() {
        return preInstantiatedBeans.stream()
                .filter(preInstantiatedBean -> preInstantiatedBean.isAnnotationPresent(Controller.class))
                .collect(Collectors.toMap(preInstantiatedBean -> preInstantiatedBean,
                        preInstantiatedBean -> beans.get(preInstantiatedBean)));
    }

    public void registerBean(final Class<?> preInstantiatedBean, final Object bean) {
        beans.put(preInstantiatedBean, bean);
    }
}
