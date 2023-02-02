package webserver.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {
    @Test
    public void create_method() {
        RequestLine line = new RequestLine("GET /index.html HTTP/1.1");
        assertEquals("GET", line.getMethod());
        assertEquals("/index.html", line.getPath());

        line = new RequestLine("POST /index.html HTTP/1.1");
        assertEquals("POST", line.getMethod());
        assertEquals("/index.html", line.getPath());
    }

    @Test
    public void create_path_and_params() {
        RequestLine line = new RequestLine("GET /index.html?hello=world&java=study HTTP/1.1");
        assertEquals("GET", line.getMethod());
        assertEquals("/index.html", line.getPath());
        assertEquals("world", line.getParams().get("hello"));
        assertEquals("study", line.getParams().get("java"));
        assertEquals(2, line.getParams().size());
    }
}