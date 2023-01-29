package customwebserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.enums.HttpMethod;

class HttpRequestTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("GET 요청에 대한 정상 파싱 테스트")
    public void request_GET() throws IOException {
        // given
        FileInputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        // when
        HttpRequest httpRequest = new HttpRequest(in);

        // then
        Assertions.assertAll(() -> {
            assertThat(httpRequest.containMethod(HttpMethod.GET)).isTrue();
            assertThat(httpRequest.getPath()).isEqualTo("/user/create");
            assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
            assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        });
    }
}