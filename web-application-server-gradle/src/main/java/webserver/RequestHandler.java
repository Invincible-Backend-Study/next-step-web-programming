package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final String RESOURCE_PATH = "./webapp";
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
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);

            List<String> lines = readHttpRequest(br);
            if (lines == null) return;

            MyHttpRequest myHttpRequest = linesToMyHttpRequest(lines);
            log.debug(RESOURCE_PATH + "   " + myHttpRequest.getRequestPath());

            String filePath = controllerMapper.mapping(myHttpRequest);
            byte[] body = null;
            if (filePath == null) {
                body = Files.readAllBytes(new File(RESOURCE_PATH + myHttpRequest.getRequestPath()).toPath());
            }
            if (filePath != null) {
                body = Files.readAllBytes(new File(RESOURCE_PATH + filePath).toPath());
            }

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static MyHttpRequest linesToMyHttpRequest(List<String> lines) {
        // RequestHttp Full
        String[] tokens = lines.get(0).split(" ");

        // url 가져오기
        String httpMethod = tokens[0];
        String url = tokens[1];
        String requestPath = url;
        Map<String, String> parameters = null;

        // request path & parameter 분리
        int index = url.indexOf("?");
        if (index != -1) {
            requestPath = url.substring(0, index);
            String params = url.substring(index + 1);

            // parameter map으로 분리
            parameters = HttpRequestUtils.parseQueryString(params);
        }
        if (index == -1) {
            requestPath = url;
        }
        log.debug("url =>" + url);
        log.debug("path =>" + requestPath);

        return new MyHttpRequest(httpMethod, requestPath, parameters);
    }

    private static List<String> readHttpRequest(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !"".equals(line)) {
            lines.add(line);
            log.debug("BufferedReader: " + line);
        }
        return lines;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
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
}
