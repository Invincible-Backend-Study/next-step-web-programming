package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import controller.MainController;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

public class RequestHandler extends Thread {
    //로깅 라이브러리
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    //소켓 클래스
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            MainController mainController = new MainController(new HttpRequest(in), new HttpResponse(out));
            mainController.runProcess();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String line = reader.readLine();
//            if (line == null) {
//                return;
//            }
//            String url = Arrays.asList(line.split(" ")).get(1);
//            HashMap<String, String> headers = new HashMap<>();
//            while (!"".equals(line)) {
//                line = reader.readLine();
//                List<String> splited = Arrays.asList(line.split(":"));
//                if (splited.size() >= 2) {
//                    headers.put(splited.get(0), splited.get(1));
//                }
//                log.debug("Header {}", line);
//            }
//            DataOutputStream dos = new DataOutputStream(out);
//
//            if (url.startsWith("/user/create")) {
//                // int index = url.indexOf("?");
//                //String bodyData = url.substring(index + 1);
//                String bodyData = IOUtils.readData(reader, Integer.parseInt(headers.get("Content-Length").trim()));
//                Map<String, String> queryString = HttpRequestUtils.parseQueryString(bodyData);
//                User user = new User(queryString.get("userId"), queryString.get("password"), queryString.get("name"),
//                        queryString.get("email"));
//                DataBase.addUser(user);
//                response302HeaderAndSetCookie(dos, "logined = true");
//                return;
//            }
//
//            if (url.equals("/user/login")) {
//                String bodyData = IOUtils.readData(reader, Integer.parseInt(headers.get("Content-Length").trim()));
//                Map<String, String> queryString = HttpRequestUtils.parseQueryString(bodyData);
//                User user = DataBase.findUserById(queryString.get("userId"));
//                if (user == null) {
//                    response302HeaderAndSetCookie(dos, "logined = false");
//                    log.debug("로그인 실패");
//                    return;
//                }
//                if (user.getPassword().equals(queryString.get("password"))) {
//                    response302HeaderAndSetCookie(dos, "logined = true");
//                    log.debug("로그인 성공");
//                } else {
//                    response302HeaderAndSetCookie(dos, "logined = false");
//                    log.debug("로그인 실패");
//                }
//                return;
//            }
//
//            if (url.startsWith("/user/list")) {
//                String cookie = headers.get("Cookie");
//                if (cookie == null) {
//                    response302Header(dos);
//                    return;
//                }
//                if (Boolean.parseBoolean(HttpRequestUtils.parseCookies(cookie).get("logined"))) {
//                    System.out.println("|||||||||||||||||");
//                    Collection<User> users = DataBase.findAll();
//                    users.forEach(userName -> {
//                        log.debug("사용자 {}", userName);
//                    });
//                }
//                response302Header(dos);
//                return;
//            }
//
//            byte[] body = Files.readAllBytes(
//                    Paths.get(new File("./web-application-server-gradle/webapp").toPath() + url));
//            response200Header(dos, body.length, headers.get("Accept"));
//            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}