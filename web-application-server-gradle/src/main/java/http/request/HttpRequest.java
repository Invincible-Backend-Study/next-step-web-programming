package http.request;

import http.request.header.HttpHeader;
import http.request.startline.HttpRequestStartLine;
import java.util.Map;
import java.util.Optional;

public interface HttpRequest {
    // 요청 타입
    // 요청 url
    // 쿼리 스트링
    HttpRequestStartLine getStartLine();

    HttpHeader getHttpHeader();

    Map<String, String> getParameters();

    String getParameterByKey(final String key);

    Optional<String> getCookie(String key);
}
