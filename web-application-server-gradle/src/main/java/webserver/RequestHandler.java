package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            // BufferedReader 값 읽기
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);

            List<String> lines = readHttpRequest(br);
            if (lines == null) return;

            // RequestUrl
            String[] tokens = lines.get(0).split(" ");
            String url = tokens[1];

            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());

            DataOutputStream dos = new DataOutputStream(out);
            // byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static List<String> readHttpRequest(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !"".equals(line)) {
            lines.add(line);
            log.debug("BufferedReader: " + line);
            // line = br.readLine();
//            if (line == null) {{
//                log.debug("null is here");
//                return null;
//            }}
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
