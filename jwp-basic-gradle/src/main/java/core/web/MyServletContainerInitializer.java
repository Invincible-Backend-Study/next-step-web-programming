package core.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

@HandlesTypes(WebApplicationInitializer.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(final Set<Class<?>> webAppInitializerClasses, final ServletContext servletContext)
            throws ServletException {
        List<WebApplicationInitializer> initializers = new LinkedList<>();

        if (webAppInitializerClasses != null) {
            for (Class<?> webAppInitializerClass : webAppInitializerClasses) {
                initializers.add(instantiate(webAppInitializerClass));
            }
        }
        if (initializers.isEmpty()) {
            servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
            return;
        }
        for (WebApplicationInitializer initializer : initializers) {
            initializer.onStartup(servletContext);
        }
    }

    private WebApplicationInitializer instantiate(final Class<?> webAppInitializerClass)
            throws ServletException {
        try {
            return (WebApplicationInitializer) webAppInitializerClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new ServletException("failed to instantiate WebApplicationInitializer class", e);
        }
    }

}
