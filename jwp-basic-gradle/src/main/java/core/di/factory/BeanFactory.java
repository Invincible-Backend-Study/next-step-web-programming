package core.di.factory;

import com.google.common.collect.Maps;
import java.lang.reflect.InvocationTargetException;
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

    public void initialize() {
        log.debug("{}", beans);
        log.info("{}", preInstantiateBeans);
        boolean retryFlag = false;
        for (final Class<?> bean : preInstantiateBeans) {
            if (beans.containsKey(bean)) {
                continue;
            }
            try {
                // 빈으로 만들어야 하는 대상에 대한 검증 로직
                final var constructorsCount = bean.getConstructors().length;
                if (constructorsCount != 1) {
                    throw new NoSuchMethodException();
                }

                // 생성하려고 하는 빈이 인터페이스의 경우
                final var constructor = bean.getDeclaredConstructors()[0];

                final var parameterTypes = constructor.getParameterTypes();

                if (parameterTypes.length == 0) {
                    final var instance = constructor.newInstance();
                    log.info("interface count {}", bean.getInterfaces().length);
                    // 인터페이스를 구현하고 있는 경우
                    if (bean.getInterfaces().length >= 1) {
                        log.info("supper class {}", bean.getInterfaces());
                        for (final var beanInterface : bean.getInterfaces()) {
                            log.info(">> interface {}", beanInterface);
                            beans.put(beanInterface, instance);
                        }
                        continue;
                    }
                    log.info(">>>");
                    beans.put(bean, instance);
                    continue;
                }

                final var parameters = new Object[parameterTypes.length];
                boolean innerFlag = false;
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!beans.containsKey(parameterTypes[i])) {
                        innerFlag = true;
                        retryFlag = true;
                        break;
                    }
                    parameters[i] = beans.get(parameterTypes[i]);
                }
                if (!innerFlag) {
                    final var instance = constructor.newInstance(parameters);
                    beans.put(bean, instance);
                }

            } catch (NoSuchMethodException e) {
                log.error("@Inject 어노테이션이 존재하지 않으며 해당 어노테이션을 제외한 다른 생성자가 존재하는 경우 해당 클래스의 Bean을 생성할 수 없습니다.{}", bean);
                throw new RuntimeException(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        if (retryFlag) {
            initialize();
        }


    }
}
