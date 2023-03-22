package next.config;

import core.di.factory.AnnotationConfigApplicationContext;
import core.di.factory.ApplicationContext;
import core.rc.AnnotationHandlerMapping;
import core.web.WebApplicationInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import lombok.extern.slf4j.Slf4j;
import next.common.web.DispatchServlet;

@Slf4j
public class MyWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("start up");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping(applicationContext);
        annotationHandlerMapping.initialize();

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatchServlet(annotationHandlerMapping));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        log.info("Start MyWebApplicationInitializer");
    }
}
