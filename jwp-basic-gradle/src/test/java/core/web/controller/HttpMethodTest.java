package core.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpMethodTest {

    @Test
    @DisplayName("HTTP 메소드 정상 반환 테스트")
    void returnHttpMethod_success() {
        // given
        List<String> methods = List.of("GET", "POST");

        // when
        List<HttpMethod> convertedMethods = methods.stream()
                .map(method -> HttpMethod.valueOf(method))
                .collect(Collectors.toList());

        // then
        assertThat(convertedMethods).containsAll(Arrays.asList(HttpMethod.values()));
    }
}