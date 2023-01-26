package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;
import webserver.http.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final ControllerMapper controllerMapper = new ControllerMapper();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // BufferedReader 값 읽기
            MyHttpRequest myHttpRequest = new MyHttpRequest(in);
            log.debug("request  ===========>" + myHttpRequest.toString());

            // 컨트롤러 맵핑 및 반환
            MyHttpResponse myHttpResponse = new MyHttpResponse(out);
            controllerMapper.mapping(myHttpRequest, myHttpResponse);
            // log.debug("Response => " + response.toString());

//            List<String> headers = response.getHeaders();
//            byte[] body = response.getBody();
//
//            // 응답 작성
//            DataOutputStream dos = new DataOutputStream(out);
//            responseHeader(dos, headers);
//            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, List<String> headers) {
        try {
            for (String header : headers) {
                dos.writeBytes(header + "\r\n");
            }
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
}
