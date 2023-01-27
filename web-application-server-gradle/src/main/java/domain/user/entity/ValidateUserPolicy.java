package domain.user.entity;

import domain.common.DomainExceptionCode;

public class ValidateUserPolicy {
    public static void validate(User user) {

        validateUserId(user.getUserId());
    }

    private static void validateUserId(String userId) {
        if (userId == null) {
            throw DomainExceptionCode.USER_ID_IS_NULL.createError();
        }
    }
}
