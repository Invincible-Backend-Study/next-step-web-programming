package next;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(WebServerLauncher.class);

    public static void main(String[] args) throws Exception {
        String servletClassPath = "build/classes";
        String webappDirLocation = "webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        StandardContext context = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        logger.info("configuring app with basedir: {}", new File("./" + webappDirLocation).getAbsolutePath());

        //-----------------------------------------------------------------------------------------
        File additionWebInfClasses = new File(servletClassPath);
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        context.setResources(resources);
        //-----------------------------------------------------------------------------------------

        tomcat.start();
        tomcat.getServer().await();
    }
}
