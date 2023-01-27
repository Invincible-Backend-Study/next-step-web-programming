package http.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpExceptionCode {
    HTTP_START_LINE_FORMAT_EXCEPTION(1000),
    HTTP_START_LINE_IS_NULL(HTTP_START_LINE_FORMAT_EXCEPTION.code + 1, "null 요청이 발생했습니다."),
    HTTP_START_LINE_SPLIT_SIZE(HTTP_START_LINE_IS_NULL.code + 2, "http 시작요청이 올바르지 않습니다. [input=%s]"),

    HTTP_REQUEST_METHOD_EXCEPTION(2000),
    HTTP_REQUEST_METHOD_DID_NOT_EXISTS(HTTP_REQUEST_METHOD_EXCEPTION.code + 1, "존재하지 않는 http method 입니다. [input=%s]"),

    HTTP_RESPONSE_STATUS_EXCEPTION(7000),
    HTTP_RESPONSE_STATUS_DID_NOT_EXISTS_CODE(HTTP_RESPONSE_STATUS_EXCEPTION.code + 1,
            "존재하지 않는 http response status 입니다. [input=%s]");
    private final Integer code;
    private final String message;

    HttpExceptionCode(Integer code) {
        this.code = code;
        this.message = "";
    }

    public HttpException newInstance() {
        return new HttpException(this.code, this.message);
    }

    public HttpException newInstance(final Object arg) {
        return new HttpException(this.code, String.format(this.message, arg));
    }

    public String getMessageFirst() {
        return this.message.split(" ")[0];
    }
}
