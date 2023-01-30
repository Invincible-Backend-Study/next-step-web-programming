package http.request;

import http.exception.HttpException;
import http.exception.HttpExceptionCode;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import testutll.FileReader;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HttpRequestParserTest {

    @Test
    void 첫번째_요청이_null_인경우_오류를_발생시킵니다() throws IOException {
        Assertions.assertThatThrownBy(() -> HttpRequestParser.parse(FileReader.read("/HTTP_null.txt")))
                .isInstanceOf(HttpException.class)
                .hasMessageContaining(HttpExceptionCode.HTTP_START_LINE_IS_NULL.getMessage());

    }

}