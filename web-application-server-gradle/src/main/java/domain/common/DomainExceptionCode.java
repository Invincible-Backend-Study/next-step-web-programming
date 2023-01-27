package domain.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum DomainExceptionCode {
    USER(100),
    PREVIOUS_SIGN_UP_USER_ID(USER.code + 1, "이미 존재하는 아이디로 회원가입을 할 수 없습니다 [input = %s]"),
    USER_ID_IS_NULL(USER.code + 2, "아이디가 빈칸 혹은 존재 하지 않습니다.");


    private static final String ERROR_FORMAT = "[ERROR] [CODE: %i] message:%s";
    private final int code;
    private final String message;

    DomainExceptionCode(int code) {
        this.code = code;
        this.message = "";
    }

    public DomainException createError(String redirectUrl, Object... args) {
        var errorMessage = String.format(message, args);
        return new DomainException(String.format(ERROR_FORMAT, code, errorMessage), redirectUrl);
    }

    public DomainException createError() {
        return new DomainException(String.format(ERROR_FORMAT, code, message));
    }
}
