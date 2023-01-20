package customwebserver.http;

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

    public HttpResponse(final OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void successMappingUri() {
        response200Header(dos, 0);
        responseBody(dos, "".getBytes());
    }

    public void successStaticUri(final String requestUri) throws IOException {
        byte[] fileBytes = getFileBytes(requestUri);
        response200Header(dos, fileBytes.length);
        responseBody(dos, fileBytes);
    }

    public void sendRedirect(final String path) throws IOException {
        byte[] fileBytes = getFileBytes(path);
        response302Header(dos, fileBytes.length, path);
        responseBody(dos, fileBytes);
    }

    private void response302Header(final DataOutputStream dos, final int lengthOfBodyContent, final String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Location: " + "http://localhost:8080" + path);
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
