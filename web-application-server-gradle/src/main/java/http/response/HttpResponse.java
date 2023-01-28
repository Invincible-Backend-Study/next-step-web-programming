package http.response;

import http.request.HttpRequest;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final HttpRequest httpRequest;
    private final List<String> responseHeaderRecord = new ArrayList<>();
    private final DataOutputStream dataOutputStream;
    private byte[] responseBodyRecord;

    public HttpResponse(HttpRequest httpRequest, DataOutputStream dataOutputStream) {
        this.httpRequest = httpRequest;
        this.dataOutputStream = dataOutputStream;
    }

    public HttpResponse status(int status) {
        return this;
    }

    public HttpResponse write(final String data) {
        this.responseHeaderRecord.add(data);
        return this;
    }


    public HttpResponse writeFile(String requestPath) throws IOException {
        final var contentType = httpRequest.getHttpHeader().get("Accept").split(",")[0];

        this.responseBodyRecord = Files.readAllBytes(new File("./webapp" + requestPath).toPath());
        this.responseHeaderRecord.add(String.format("Content-Type: %s;charset=utf-8", contentType));
        this.responseHeaderRecord.add("Content-Length: " + responseBodyRecord.length);
        return this;
    }

    public void send() throws IOException {
        log.info("{}", String.join("\r\n", this.responseHeaderRecord));

        final var headerToString = this.responseHeaderRecord.stream().map(record -> record + "\r\n").collect(Collectors.joining());

        dataOutputStream.writeBytes(headerToString);
        dataOutputStream.writeBytes("\r\n");

        if (responseBodyRecord != null) {
            dataOutputStream.write(responseBodyRecord, 0, responseBodyRecord.length);
        }

        dataOutputStream.flush();
    }

    public HttpResponse writeRedirect(String redirectUrl) {
        this.responseHeaderRecord.add("HTTP/1.1 302 FOUND");
        this.responseHeaderRecord.add("Location: " + redirectUrl);
        return this;
    }

    public HttpResponse writeHtml(String generateView) {
        this.responseHeaderRecord.add("HTTP/1.1 202 OK");
        this.responseHeaderRecord.add("Content-Type: text/html;charset=utf-8");
        this.responseBodyRecord = generateView.getBytes(StandardCharsets.UTF_8);
        return this;
    }
}
