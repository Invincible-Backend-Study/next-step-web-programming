package core.web;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@HandlesTypes({WebApplicationInitializer.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    @SneakyThrows
    private void initialize(ServletContext servletContext, WebApplicationInitializer initializer) {
        initializer.onStartup(servletContext);
    }

    @SneakyThrows
    private WebApplicationInitializer newInstance(Class<?> webAppInitializerClass) {
        return (WebApplicationInitializer) webAppInitializerClass.getDeclaredConstructor().newInstance();
    }

    @Override
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) {
        log.info("{}", webAppInitializerClasses);
        log.info("my servlet container initializer");
        if (webAppInitializerClasses == null || webAppInitializerClasses.isEmpty()) {
            servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
            return;
        }
        webAppInitializerClasses.stream()
                .map(this::newInstance)
                .forEach(initializer -> initialize(servletContext, initializer));
    }
}
