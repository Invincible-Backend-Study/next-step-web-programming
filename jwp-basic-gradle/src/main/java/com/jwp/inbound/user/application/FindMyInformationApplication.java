package com.jwp.inbound.user.application;

import com.jwp.inbound.user.port.driven.UserPort;
import com.jwp.inbound.user.port.driving.FindMyInformationUseCase;
import com.jwp.inbound.user.port.driving.dto.EditMyInformation;
import core.annotation.Inject;
import core.annotation.Service;
import next.common.error.DomainExceptionCode;

@Service
public class FindMyInformationApplication implements FindMyInformationUseCase {
    private final UserPort userPort;


    @Inject
    public FindMyInformationApplication(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public EditMyInformation execute(String userId) {
        return EditMyInformation.from(userPort.findById(userId).orElseThrow(DomainExceptionCode.USER_ID_IS_NULL::createError));
    }
}
