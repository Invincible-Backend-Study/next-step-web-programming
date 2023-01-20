package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.http.MyHttpRequest;
import webserver.http.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            MyHttpRequest myHttpRequest = httpRequestFromInputStream(in);
            log.debug("request  ===========>" + myHttpRequest.toString());

            // 동작 맵핑 및 반환
            Response response = controllerMapper.mapping(myHttpRequest);
            log.debug("Response => " + response.toString());

            List<String> headers = response.getHeaders();
            byte[] body = response.getBody();

            // 응답 작성
            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos, headers);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static MyHttpRequest httpRequestFromInputStream(InputStream in) throws IOException {
        // BufferedReader 값 읽기
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);

        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !"".equals(line)) {
            lines.add(line);
            log.debug("BufferedReader: " + line);
        }

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
        for (String l : lines) {
            if ("".equals(l)) {
                break;
            }
            String[] headerTokens = l.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        log.debug("contentLength:" + headers.get("Content-Length"));

        // request body 읽기
        String requestBody = null;
        if (headers.get("Content-Length") != null) {
            requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            if (!"".equals(requestBody)) {
                log.debug("requestBody: " + requestBody);
                if ("POST".equals(httpMethod)) {
                    parameters = HttpRequestUtils.parseQueryString(requestBody);
                }
            }
        }

        return new MyHttpRequest(httpMethod, requestPath, parameters, headers, requestBody);
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
