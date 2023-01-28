package webserver;

import http.request.HttpRequestParser;
import http.response.HttpResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            final var dataOutputStream = new DataOutputStream(out);
            final var httpRequest = HttpRequestParser.parse(in);
            final var httpResponse = new HttpResponse(httpRequest, dataOutputStream);

            ApplicationContext.process(httpRequest, httpResponse);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
