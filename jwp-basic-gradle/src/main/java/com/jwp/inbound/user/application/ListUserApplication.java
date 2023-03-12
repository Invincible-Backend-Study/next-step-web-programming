package com.jwp.inbound.user.application;

import com.google.common.collect.Lists;
import com.jwp.inbound.user.port.driving.ListUserUseCase;
import com.jwp.inbound.user.port.driving.dto.ReadOnlyUser;
import core.annotation.Service;
import java.util.List;

@Service
public class ListUserApplication implements ListUserUseCase {
    @Override
    public List<ReadOnlyUser> execute() {
        return Lists.newArrayList(ReadOnlyUser.of("1234", "123", "123"));
    }
}
