package com.jwp.inbound.user.port.driving;

import com.jwp.outbound.user.adapter.driving.request.CreateUserRequest;

public interface SignUpUseCase {
    void execute(CreateUserRequest createUserRequest);
}
