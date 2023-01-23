package http.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpExceptionCode {
    HTTP_START_LINE_FORMAT_EXCEPTION(1000),
    HTTP_START_LINE_IS_NULL(HTTP_START_LINE_FORMAT_EXCEPTION.code + 1, "null 요청이 발생했습니다.");

    private final Integer code;
    private final String message;

    HttpExceptionCode(Integer code) {
        this.code = code;
        this.message = "";
    }

    public HttpException newInstance() {
        return new HttpException(this.code, this.message);
    }

}
