package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

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
            if (lines == null) {
                return;
            }
            MyHttpRequest myHttpRequest = linesToMyHttpRequest(br, lines);
            log.debug("request  ===========>" + myHttpRequest.toString());

            // 동작 맵핑 및 반환
            Response response = controllerMapper.mapping(myHttpRequest);

            boolean auth = false;
            String httpStatus = "200 OK";
            String resourcePath = myHttpRequest.getRequestPath();
            if (response != null) {
                log.debug("response ===========>" + response.toString());
                resourcePath = response.getPath();
                httpStatus = response.getHttpStatus();
                auth = response.getAuth();
            }

            // 응답 작성
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + resourcePath).toPath());
            responseHeader(dos, body.length, httpStatus, auth);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static MyHttpRequest linesToMyHttpRequest(BufferedReader br, List<String> lines) throws IOException {
        // RequestHttp Full
        String[] tokens = lines.get(0).split(" ");
        lines.remove(0);

        // url 가져오기
        String httpMethod = tokens[0];
        String url = tokens[1];
        String requestPath = url;
        Map<String, String> parameters = null;
        Map<String, String> headers = new HashMap<String, String>();

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



        // header 읽기
        for (String line : lines) {
            if ("".equals(line)) {
                break;
            }
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        log.debug("contentLength:" + headers.get("Content-Length"));

        // request body 읽기
        if (headers.get("Content-Length") != null) {
            String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            if (!"".equals(requestBody)) {
                log.debug("requestBody: " + requestBody);
                parameters = HttpRequestUtils.parseQueryString(requestBody);
            }
        }

        return new MyHttpRequest(headers, httpMethod, requestPath, parameters);
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

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, String httpStatus, boolean isLogin) {
        try {
            dos.writeBytes("HTTP/1.1 " + httpStatus + " \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            if (isLogin) {
                dos.writeBytes("Set-Cookie: " + "logined=true");
            }
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
