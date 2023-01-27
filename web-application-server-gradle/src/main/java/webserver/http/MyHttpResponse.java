package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class MyHttpResponse {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final DataOutputStream dos;
    private Map<String, String> headers = new HashMap<String, String>();

    public MyHttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void forward(String url) {
        try {
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            if (url.endsWith(".css")) {
                addHeader("Content-Type", "text/css");
            }
            if (url.endsWith(".js")) {
                addHeader("Content-Type", "application/javascript");
            }
            if (url.endsWith(".html")) {
                addHeader("Content-Type", "text/html;charset=utf-8");
            }
            addHeader("Content-Length", body.length + "");
            response200Header(body.length);
            responseBody(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void forwardBody(byte[] contents) {
        addHeader("Content-Type", "text/html;charset=utf-8");
        addHeader("Content-Length", contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        addHeader("Content-Type", "text/html;charset=utf-8");
        addHeader("Content-Length", contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.writeBytes("Location: " + redirectUrl + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processHeaders() {
        try {
            for (String header : headers.keySet()) {
                dos.writeBytes(header + ": " + headers.get(header) + "\r\n");
            }
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
}
