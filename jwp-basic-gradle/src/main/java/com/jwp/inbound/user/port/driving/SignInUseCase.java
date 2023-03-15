package com.jwp.inbound.user.port.driving;

import com.jwp.inbound.user.port.driving.dto.ReadOnlyUser;

public interface SignInUseCase {
    ReadOnlyUser execute(final String userId, final String password);
}
