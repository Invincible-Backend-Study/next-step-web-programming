package customwebserver.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;
    private final Map<String, Object> cookies = new HashMap<>();
    private String responseBodyData;

    public HttpResponse(final OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void forward(final String path) {

    }

    public void successMappingUri() {
        if (responseBodyData != null) {
            byte[] dataBytes = responseBodyData.getBytes();
            response200HeaderWithBody(dataBytes.length);
            responseBody(dataBytes);
            return;
        }
        response200Header(0);
        responseBody("".getBytes());
    }

    public void successStaticUri(final String requestUri) throws IOException {
        byte[] fileBytes = getFileBytes(requestUri);
        response200Header(fileBytes.length);
        responseBody(fileBytes);
    }

    public void successStaticCss(final String requestUri) throws IOException {
        byte[] fileBytes = getFileBytes(requestUri);
        response200HeaderWithCss(fileBytes.length);
        responseBody(fileBytes);
    }

    public void sendRedirect(final String path) throws IOException {
        byte[] fileBytes = getFileBytes(path);
        response302Header(fileBytes.length, path);
        responseBody(fileBytes);
    }

    public void addCookie(final String cookieKey, final Object value) {
        cookies.put(cookieKey, value);
    }

    public void sendResponseBody(final String data) {
        this.responseBodyData = data;
    }

    private String getAllCookieMessage() {
        StringBuilder cookieMessage = new StringBuilder();
        cookieMessage.append("Set-Cookie: ");
        if (!cookies.isEmpty()) {
            cookies.keySet()
                    .forEach(key -> cookieMessage.append(key)
                            .append("=")
                            .append(cookies.get(key))
                            .append("; "));
            return cookieMessage.append(" path=/")
                    .append("\r\n")
                    .toString();
        }
        return "";
    }

    private void response302Header(final int lengthOfBodyContent, final String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes(getAllCookieMessage());
            dos.writeBytes("Location: " + path + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes(getAllCookieMessage());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200HeaderWithCss(final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes(getAllCookieMessage());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200HeaderWithBody(final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/plain;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes(getAllCookieMessage());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
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
