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

    public void successMappingUri() {
        if (responseBodyData != null) {
            byte[] dataBytes = responseBodyData.getBytes();
            response200HeaderWithBody(dos, dataBytes.length);
            responseBody(dos, dataBytes);
            return;
        }
        response200Header(dos, 0);
        responseBody(dos, "".getBytes());
    }

    public void successStaticUri(final String requestUri) throws IOException {
        byte[] fileBytes = getFileBytes(requestUri);
        response200Header(dos, fileBytes.length);
        responseBody(dos, fileBytes);
    }

    public void successStaticCss(final String requestUri) throws IOException {
        byte[] fileBytes = getFileBytes(requestUri);
        response200HeaderWithCss(dos, fileBytes.length);
        responseBody(dos, fileBytes);
    }

    public void sendRedirect(final String path) throws IOException {
        byte[] fileBytes = getFileBytes(path);
        response302Header(dos, fileBytes.length, path);
        responseBody(dos, fileBytes);
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
            return cookieMessage.delete(cookieMessage.length() - 2, cookieMessage.length())
                    .append("\r\n")
                    .toString();
        }
        return "";
    }

    private void response302Header(final DataOutputStream dos, final int lengthOfBodyContent, final String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes(getAllCookieMessage());
            dos.writeBytes("Location: http://localhost:8080" + path + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(final DataOutputStream dos, final int lengthOfBodyContent) {
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

    private void response200HeaderWithCss(final DataOutputStream dos, final int lengthOfBodyContent) {
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

    private void response200HeaderWithBody(final DataOutputStream dos, final int lengthOfBodyContent) {
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static byte[] getFileBytes(final String requestUri) throws IOException {
        byte[] fileBytes = Files.readAllBytes(new File("./webapp" + requestUri).toPath());
        return fileBytes;
    }
}
