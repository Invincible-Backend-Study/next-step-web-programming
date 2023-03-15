package com.jwp.inbound.user.application;

import com.jwp.inbound.user.port.driven.UserPort;
import com.jwp.inbound.user.port.driving.ListUserUseCase;
import com.jwp.inbound.user.port.driving.dto.ReadOnlyUser;
import core.annotation.Inject;
import core.annotation.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListUserApplication implements ListUserUseCase {
    private final UserPort userPort;


    @Inject
    public ListUserApplication(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public List<ReadOnlyUser> execute() {
        return userPort.findAll().stream().map(ReadOnlyUser::of).collect(Collectors.toList());
    }
}
