package core.di.factory.inject;

import com.google.common.collect.Lists;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public abstract class AbstractInjector {
    private static final Logger logger = LoggerFactory.getLogger(AbstractInjector.class);
    protected final BeanFactory beanFactory;
    protected final Set<Class<?>> preInstanticateBeans;
    public AbstractInjector(BeanFactory beanFactory, Set<Class<?>> preInstanticateBeans) {
        this.beanFactory = beanFactory;
        this.preInstanticateBeans = preInstanticateBeans;
    }
    public void inject(Class<?> clazz) {
        if (beanFactory.getBean(clazz) == null) {
            Object object = instantiate(clazz);
            beanFactory.putBean(clazz, object);
        }
    }

    Object instantiate(Class<?> clazz) {
        Object bean = beanFactory.getBean(clazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> constructor = getInjectedConstructor(clazz);
        if (constructor == null) {
            bean = BeanUtils.instantiate(clazz);  // 내부적으로 newInstance() 동작
            beanFactory.putBean(clazz, bean);
            return bean;
        }

        logger.debug("Constructor : {}", constructor);
        bean = instantiateConstructor(constructor);
        beanFactory.putBean(clazz, bean);
        return bean;
    }

    /**
     * 특정 클래스의 @Inject 된 obejct의 생성자를 내부적으로 가져온다.
     * @param clazz
     * @return
     */
    Constructor<?> getInjectedConstructor(Class<?> clazz) {
        return null;
    }

    Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : parameterTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            if (!preInstanticateBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }

            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiate(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
