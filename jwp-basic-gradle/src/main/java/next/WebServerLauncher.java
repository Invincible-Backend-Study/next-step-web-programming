package next;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class WebServerLauncher {
    public static void main(String[] args) throws ServletException, LifecycleException {
        String webappPath = "./webapp";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/",new File(webappPath).getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }
}
