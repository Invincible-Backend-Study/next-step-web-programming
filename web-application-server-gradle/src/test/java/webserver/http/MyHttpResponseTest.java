package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class MyHttpResponseTest {
    private static final String testDirectory = "./src/test/resources/http/response/";


    @Test
    public void responseForward() throws FileNotFoundException {
        MyHttpResponse response = new MyHttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }


    @Test
    public void responseRedirect() throws Exception {
        MyHttpResponse response = new MyHttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        MyHttpResponse response = new MyHttpResponse(createOutputStream("Http_Cookies.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}