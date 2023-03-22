package next.config;

import core.web.mvcframework.DispatcherServlet;
import core.web.mvcframework.mapping.HandlerMapping;
import core.web.mvcframework.mapping.annotation.AnnotationHandlerMapping;
import core.web.WebApplicationInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        HandlerMapping handlerMapping = new AnnotationHandlerMapping("next", "core");
        handlerMapping.initialize();
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(handlerMapping));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }

}
