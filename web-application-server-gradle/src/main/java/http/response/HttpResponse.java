package http.response;

import http.request.HttpRequest;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
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

    // status code
    // content-type content
    // cookie
    // location
    // response header
    // response body
    public HttpResponse status(int status) {
        return this;
    }

    public HttpResponse write(final String data) throws IOException {
        this.responseHeaderRecord.add(data);
        return this;
    }


    public HttpResponse writeFile(String requestPath) throws IOException {
        this.responseBodyRecord = Files.readAllBytes(new File("./webapp" + requestPath).toPath());
        var contentType = httpRequest.getHttpHeader().get("Accept").split(",")[0];

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
    // 응답에 대한 처리를 상태패턴으로 처리할 수 있을 것 같음
    // 응답요청 처리 상태 -> 응답 헤더 처리 상태 -> 응답 바디 처리 상태
    // 응답 헤더 처리 상태 -> 쿠키 설정
    //                -> content-type설정

}
