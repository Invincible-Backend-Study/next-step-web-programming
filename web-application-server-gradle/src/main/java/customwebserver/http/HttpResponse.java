package customwebserver.http;

import customwebserver.http.cookie.HttpCookies;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final HttpCookies httpCookies = new HttpCookies();

    public HttpResponse(final OutputStream out) {
        dos = new DataOutputStream(out);
    }

    /**
     * html, css, js와 같은 정적파일을 응답할때 사용됨
     */
    public void forward(final String path) throws IOException {
        byte[] fileBytes = getFileBytes(path);
        putStaticContentTypeHeader(path);
        httpHeaders.addHeader("Content-Length", String.valueOf(fileBytes.length));
        response200Header();
        responseBody(fileBytes);
    }

    private void putStaticContentTypeHeader(final String path) {
        if (path.endsWith(".css")) {
            httpHeaders.addHeader("Content-Type", "text/css;charset=utf-8");
        }
        if (path.endsWith(".js")) {
            httpHeaders.addHeader("Content-Type", "application/javascript");
        }
        if (path.endsWith(".html")) {
            httpHeaders.addHeader("Content-Type", "text/html;charset=utf-8");
        }
    }

    public void sendRedirect(final String path) throws IOException {
        byte[] fileBytes = getFileBytes(path);
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + fileBytes.length + "\r\n");
            writeHeaders();
            dos.writeBytes(httpCookies.createCookieMessage());
            dos.writeBytes("Location: " + path + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        responseBody(fileBytes);
    }

    public void sendResponseBody(final String data) {
        byte[] dataBytes = data.getBytes();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/plain;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + dataBytes.length + "\r\n");
            dos.writeBytes(httpCookies.createCookieMessage());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        responseBody(dataBytes);
    }

    public void addCookie(final String cookieKey, final String value) {
        httpCookies.addCookie(cookieKey, value);
    }

    public void addHeader(final String header, final String value) {
        httpHeaders.addHeader(header, value);
    }

    private void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            writeHeaders();
            dos.writeBytes(httpCookies.createCookieMessage());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeHeaders() throws IOException {
        dos.writeBytes(httpHeaders.createHeaderMessage());
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static byte[] getFileBytes(final String requestUri) throws IOException {
        return Files.readAllBytes(new File("./webapp" + requestUri).toPath());
    }
}
