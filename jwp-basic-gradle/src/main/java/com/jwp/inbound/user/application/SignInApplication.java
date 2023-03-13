package com.jwp.inbound.user.application;

import com.jwp.inbound.user.port.driven.UserPort;
import com.jwp.inbound.user.port.driving.SignInUseCase;
import com.jwp.inbound.user.port.driving.dto.ReadOnlyUser;
import core.annotation.Inject;
import core.annotation.Service;
import next.common.error.DomainExceptionCode;

@Service
public class SignInApplication implements SignInUseCase {
    private final UserPort userPort;

    @Inject
    public SignInApplication(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public ReadOnlyUser execute(String userId, String password) {
        final var user = userPort.findById(userId).orElseThrow(DomainExceptionCode.USER_ID_IS_NULL::createError);

        if (!user.comparePassword(password)) {
            throw DomainExceptionCode.USER_PASSWORD_DID_NOT_CORRECT.createError();
        }
        return ReadOnlyUser.of(user);

    }
}
