package http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final DataOutputStream dos;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    private void response200Header(final DataOutputStream dos, int lengthOfBodyContent, String accept) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type:" + accept.split(",")[0].trim() + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200HeaderWithCss(final DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302HeaderAndSetCookie(final DataOutputStream dos, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(final DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(final DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
        }
    }

    public void responseStaticFile(final HttpRequest httpRequest) throws IOException {
        byte[] body = Files.readAllBytes(
                Paths.get(new File("./web-application-server-gradle/webapp").toPath() + httpRequest.getUrl()));
        response200Header(dos, body.length, httpRequest.getHeaders("Accept"));
        responseBody(dos, body);
    }

    public void redirectHome() {
        response302Header(dos);
    }

    public void loginSucess() {
        response302HeaderAndSetCookie(dos, "logined = true;Path=/;");
        redirectHome();
    }

    public void loginFailed() {
        response302HeaderAndSetCookie(dos, "logined = false;Path=/;");
        redirectHome();
    }

    public void response404Header(HttpRequest httpRequest) throws IOException {
        byte[] body = Files.readAllBytes(
                Paths.get(new File("./web-application-server-gradle/webapp").toPath() + "/404.html"));
        response200Header(dos, body.length, httpRequest.getHeaders("Accept"));
        responseBody(dos, body);
    }
}
