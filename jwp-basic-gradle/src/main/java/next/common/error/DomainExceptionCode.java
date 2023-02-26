package next.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum DomainExceptionCode {
    USER(100),

    /**
     * 이미 존재하는 아이디에 대한 사용자를 생성할 수 없습니다.
     */
    PREVIOUS_SIGN_UP_USER_ID(USER.code + 1, "이미 존재하는 아이디로 회원가입을 할 수 없습니다 [input = %s]"),
    PREVIOUS_SIGN_UP_USER_NAME(USER.code + 6, "이미존재하는 이름으로 회원가입을 할 수 없습니다 [input = %s]"),

    USER_ID_IS_NULL(USER.code + 2, "아이디가 빈칸 혹은 존재하지 않습니다."),
    USER_PASSWORD_IS_NULL(USER.code + 3, "사용자 비밀번호가 빈칸이거나 존재하지 않습니다."),
    USER_NAME_IS_NULL(USER.code + 4, "사용자 이름이 빈칸이거나 존재하지 않습니다."),
    USER_EMAIL_IS_NULL(USER.code + 5, "사용자 이메일이 빈칸이거나 존재하지 않습니다."),


    QUESTION(4000),
    DID_NOT_EXISTS_QUESTION_ID(QUESTION.code + 1, "해당 질문이 존재하지 않습니다."),
    DID_NOT_DELETE_QUESTION(QUESTION.code + 2, "작성자 본인이 작성하지 않은 답변이 %s건 있어 해당 질문을 삭제할 수 없습니다.");


    private static final String ERROR_FORMAT = "[ERROR] [CODE: %s] message:%s";
    private final int code;
    private final String message;

    DomainExceptionCode(int code) {
        this.code = code;
        this.message = "";
    }

    public DomainException createError() {
        return new DomainException(String.format(ERROR_FORMAT, code, message));
    }

    public DomainException createError(Object... args) {
        String generatedErrorMessage = this.generateErrorMessage(args);
        return new DomainException(this.generateMessage(generatedErrorMessage));
    }

    private String generateMessage(final String message) {
        return String.format(ERROR_FORMAT, code, message);
    }

    private String generateErrorMessage(Object... args) {
        return String.format(this.message, args);
    }

    public String getMessage(Object... args) {
        return this.generateMessage(this.generateErrorMessage(args));
    }
}