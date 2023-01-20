package webserver;

import db.DataBase;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    private static Map<String, String> parseBodyParams(BufferedReader bufferedReader,
                                                       int contentLength) throws IOException {
        var bodyData = IOUtils.readData(bufferedReader, contentLength);
        return HttpRequestUtils.parseQueryString(bodyData);
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            var inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            var bufferedReader = new BufferedReader(inputStreamReader);

            var startLine = bufferedReader.readLine();

            if (startLine == null) {
                return;
            }
            // 첫 줄에 대한 요청을 처리함
            var splitStr = startLine.split(" ");

            var url = splitStr[1]; // 첫줄의 1번째 인덱스에는 Url이 위치함

            // url에서 파라미터가 존재하는 경우가 있을 수 있음
            var delimiterIndex = url.indexOf('?');

            var requestPath = url;
            if (delimiterIndex != -1) {
                requestPath = url.substring(0, delimiterIndex);
            }

            var httpHeaders = new LinkedHashMap<String, String>();
            var line = bufferedReader.readLine();
            while (!"".equals(line)) {
                var pair = HttpRequestUtils.parseHeader(line);
                httpHeaders.put(pair.getKey(), pair.getValue());
                line = bufferedReader.readLine();
            }

            // 회원가입을 처리하기 위한 경로
            if (requestPath.equals("/user/create")) {
                Map<String, String> bodyParams = parseBodyParams(bufferedReader,
                        Integer.parseInt(httpHeaders.get("Content-Length").trim()));
                var user = User.builder()
                        .userId(bodyParams.get("userId"))
                        .password(bodyParams.get("password"))
                        .email(bodyParams.get("email"))
                        .name(bodyParams.get("name"))
                        .build();
                DataBase.addUser(user);

                log.info(DataBase.findUserById(bodyParams.get("userId")).toString());

                url = "/index.html";
                DataOutputStream dos = new DataOutputStream(out);
                responseRedirectHeader(dos, url);
                return;
            }
            // 로그인을 처리하기 위한 경로
            if (requestPath.equals("/user/login")) {
                Map<String, String> bodyParams = parseBodyParams(bufferedReader,
                        Integer.parseInt(httpHeaders.get("Content-Length").trim()));

                var userId = bodyParams.get("userId");
                var password = bodyParams.get("password");

                var findedUser = DataBase.findUserById(userId);

                DataOutputStream dos = new DataOutputStream(out);
                if (findedUser == null) {
                    url = "/user/login_failed.html";
                    responseLoginHeader(dos, url, false);
                    return;
                }
                if (findedUser.samePassword(password)) {
                    url = "/index.html";
                    responseLoginHeader(dos, url, true);
                    return;
                }
                url = "/user/login_failed.html";
                responseLoginHeader(dos, url, false);
                return;
            }
            if (url.equals("/user/list")) {
                DataOutputStream dos = new DataOutputStream(out);
                if (!httpHeaders.containsKey("Cookie")) {
                    responseRedirectHeader(dos, "user/login.html");
                    return;
                }
                var parseCookies = HttpRequestUtils.parseCookies(httpHeaders.get("Cookie"));
                if (!parseCookies.containsKey("logined")) {
                    responseRedirectHeader(dos, "/user/login.html");
                    return;
                }
                if (!Boolean.parseBoolean(parseCookies.get("logined"))) {
                    responseRedirectHeader(dos, "/user/login.html");
                    return;
                }

                var users = DataBase.findAll();
                var html = "<!DOCTYPE HTML>" // 인텔리제이가 스트링빌더를 자꾸 평문으로 바꿈
                        + "<head>"
                        + "</head>"
                        + "<body>"
                        + "<ul>"
                        + users.stream().map(user -> String.format("<li>%s</lis>", user.getName()))
                        .collect(Collectors.joining())
                        + "</ul>"
                        + "</body>"
                        + "</html>";
                responseHtml(new DataOutputStream(out), html);
                return;
            }

            var body = Files.readAllBytes(new File("./webapp" + url).toPath());
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length, httpHeaders.get("Accept").split(",")[0]);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseHtml(DataOutputStream dos, String html) {
        try {
            dos.writeBytes("HTTP/1.1 202 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            dos.writeBytes(html + "\r\n");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseLoginHeader(DataOutputStream dos, String location, boolean isLoggedIn) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Set-Cookie: logined=" + isLoggedIn + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseRedirectHeader(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
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
