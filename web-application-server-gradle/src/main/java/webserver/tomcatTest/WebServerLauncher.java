package webserver.tomcatTest;

import java.io.File;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServerLauncher {
    private static final Logger log = LoggerFactory.getLogger(WebServerLauncher.class);

    public static void main(String[] args) throws Exception {
        String webappDirLocation = "webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/",
                new File(webappDirLocation).getAbsolutePath());

        log.info("config app with baseDir: {}",
                new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().wait();
    }
}
