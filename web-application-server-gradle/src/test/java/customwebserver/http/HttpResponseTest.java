package customwebserver.http;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseTest {

    @Test
    @DisplayName("정적 파일 응답 정상 반환 테스트")
    void responseForward() throws IOException {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }


    @Test
    @DisplayName("리다이렉트 정상 응답 테스트")
    void responseRedirect() throws IOException {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    @DisplayName("로그인 성공시 쿠키 정상 생성 테스트")
    void responseCookies() throws IOException {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(final String fileName) throws FileNotFoundException {
        return new FileOutputStream(new File("./src/test/resources/" + fileName));
    }
}