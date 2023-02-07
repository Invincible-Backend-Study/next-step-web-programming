package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.MyHttpRequest;
import webserver.http.MyHttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        // log.debug("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // BufferedReader 값 읽기
            MyHttpRequest myHttpRequest = new MyHttpRequest(in);
            MyHttpResponse myHttpResponse = new MyHttpResponse(out);

            // 신규 클라이언트에 대한 Session 처리 - 해당 구문이 실행된다는 건 어차피 지금 세션에는 아무 내용도 없다는 것
            if (myHttpRequest.getSessionId() == null) {
                myHttpResponse.addHeader("Set-Cookie", "JSESSIONID=" + UUID.randomUUID());
            }

            // 컨트롤러 맵핑 및 동작
            Controller controller = ControllerMapper.getController(myHttpRequest.getRequestPath());
            controller.service(myHttpRequest, myHttpResponse);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
