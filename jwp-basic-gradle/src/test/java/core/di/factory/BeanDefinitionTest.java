package core.di.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanDefinitionTest {

    private static final Logger log = LoggerFactory.getLogger(BeanDefinitionTest.class);

    @Test
    public void getResolvedAutowireMode() {
        BeanDefinition dbd = new BeanDefinition(JdbcUserRepository.class);
        assertEquals(InjectType.INJECT_NO, dbd.getResolvedInjectMode());

        dbd = new BeanDefinition(MyUserController.class);
        assertEquals(InjectType.INJECT_FIELD, dbd.getResolvedInjectMode());

        dbd = new BeanDefinition(MyQnaService.class);
        assertEquals(InjectType.INJECT_CONSTRUCTOR, dbd.getResolvedInjectMode());
    }

    @Test
    public void getInjectProperties() throws Exception {
        BeanDefinition dbd = new BeanDefinition(MyUserController.class);
        Set<Field> injectFields = dbd.getInjectedFields();
        for (Field field : injectFields) {
            log.debug("inject field : {}", field);
        }
    }

    @Test
    public void getConstructor() throws Exception {
        BeanDefinition dbd = new BeanDefinition(MyQnaService.class);
        Set<Field> injectFields = dbd.getInjectedFields();
        assertEquals(0, injectFields.size());
        Constructor<?> constructor = dbd.getInjectConstructor();
        log.debug("inject constructor : {}", constructor);
    }
}
