package com.jwp.inbound.user.port.driving;

import com.jwp.inbound.user.port.driving.dto.EditMyInformation;

public interface FindMyInformationUseCase {

    EditMyInformation execute(final String userId);
}
