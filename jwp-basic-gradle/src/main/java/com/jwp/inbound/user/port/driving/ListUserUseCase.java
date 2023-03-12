package com.jwp.inbound.user.port.driving;

import com.jwp.inbound.user.port.driving.dto.ReadOnlyUser;
import java.util.List;

public interface ListUserUseCase {
    List<ReadOnlyUser> execute();
}
