package domain.user.entity;

import domain.common.DomainExceptionCode;

public class ValidateUserPolicy {
    public static void validate(User user) {
        validateUserId(user.getUserId());
        validateUsername(user.getName());
        validateUserPassword(user.getPassword());
        validateUserEmail(user.getEmail());
    }

    private static void validateUserEmail(String email) {
        if (email == null) {
            throw DomainExceptionCode.USER_EMAIL_IS_NULL.createError();
        }
    }

    private static void validateUserPassword(String name) {
        if (name == null) {
            throw DomainExceptionCode.USER_NAME_IS_NULL.createError();
        }

    }

    private static void validateUsername(String password) {
        if (password == null) {
            throw DomainExceptionCode.USER_PASSWORD_IS_NULL.createError();
        }

    }

    private static void validateUserId(String userId) {
        if (userId == null) {
            throw DomainExceptionCode.USER_ID_IS_NULL.createError();
        }
    }
}
