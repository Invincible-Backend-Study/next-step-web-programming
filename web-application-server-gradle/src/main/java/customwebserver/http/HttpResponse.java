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
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> cookies = new HashMap<>();
    private String responseBodyData;

    public HttpResponse(final OutputStream out) {
        dos = new DataOutputStream(out);
    }

    /**
     * html, css, js와 같은 정적파일을 응답할때 사용됨
     */
    public void forward(final String path) throws IOException {
        byte[] fileBytes = getFileBytes(path);
        selectStaticContentType(path);
        headers.put("Content-Length", String.valueOf(fileBytes.length));
        response200Header();
        responseBody(fileBytes);
    }

    private void selectStaticContentType(final String path) {
        if (path.endsWith(".css")) {
            headers.put("Content-Type", "text/css;charset=utf-8");
        }
        if (path.endsWith(".js")) {
            headers.put("Content-Type", "application/javascript");
        }
        if (path.endsWith(".html")) {
            headers.put("Content-Type", "text/html;charset=utf-8");
        }
    }


    public void successMappingUri() {
        if (responseBodyData != null) {
            byte[] dataBytes = responseBodyData.getBytes();
            response200HeaderWithBody(dataBytes.length);
            responseBody(dataBytes);
            return;
        }
        response200Header();
        responseBody("".getBytes());
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

    private void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            writeHeaders();
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

    private void writeHeaders() {
        headers.keySet()
                .stream()
                .map(key -> key + ": " + headers.get(key) + "\r\n")
                .forEach(header -> {
                    try {
                        dos.writeBytes(header);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                });
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
