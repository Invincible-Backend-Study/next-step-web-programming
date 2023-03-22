package core.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface WebApplicationInitializer {

    void onStartup(final ServletContext servletContext) throws ServletException;

}
