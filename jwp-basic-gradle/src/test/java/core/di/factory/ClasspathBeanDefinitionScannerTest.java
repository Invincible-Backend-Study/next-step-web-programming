package core.di.factory;

import org.junit.jupiter.api.Test;

class ClasspathBeanDefinitionScannerTest {


    @Test
    void 테스트() {
        final var beanFactory = new BeanFactory();
        final var classpathBeanDefinitionScanner = new ClasspathBeanDefinitionScanner(beanFactory);

        classpathBeanDefinitionScanner.doScan("core");
        beanFactory.initialize();
    }

}