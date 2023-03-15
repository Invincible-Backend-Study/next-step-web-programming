package com.jwp.inbound.user.application;


import com.jwp.inbound.user.port.driven.UserPort;
import com.jwp.inbound.user.port.driving.SignUpUseCase;
import com.jwp.outbound.user.adapter.driving.request.CreateUserRequest;
import core.annotation.Inject;
import core.annotation.Service;
import next.common.error.DomainExceptionCode;

@Service
public class SignUpApplication implements SignUpUseCase {
    private final UserPort userPort;


    @Inject
    public SignUpApplication(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public void execute(CreateUserRequest createUserRequest) {
        final var optionalUser = userPort.findById(createUserRequest.getUserId());

        if (optionalUser.isPresent()) {
            throw DomainExceptionCode.PREVIOUS_SIGN_UP_USER_ID.createError();
        }
        userPort.create(createUserRequest.toUser());
    }
}
