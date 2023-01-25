package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MyHttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.http"));
        MyHttpRequest myHttpRequest = new MyHttpRequest(in);

        assertEquals("GET", myHttpRequest.getHttpMethod());
        assertEquals("/user/create", myHttpRequest.getRequestPath());
        assertEquals("keep-alive", myHttpRequest.getHeader("Connection"));
        assertEquals("myid", myHttpRequest.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.http"));
        MyHttpRequest myHttpRequest = new MyHttpRequest(in);

        assertEquals("POST", myHttpRequest.getHttpMethod());
        assertEquals("/user/create", myHttpRequest.getRequestPath());
        assertEquals("keep-alive", myHttpRequest.getHeader("Connection"));
        assertEquals("myid", myHttpRequest.getParameter("userId"));
    }
}
