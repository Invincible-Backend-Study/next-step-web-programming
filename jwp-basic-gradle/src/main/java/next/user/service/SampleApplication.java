package next.user.service;

import core.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import next.user.controller.usecase.SampleUseCase;


@Slf4j
@Service
public class SampleApplication implements SampleUseCase {


    @Override
    public void execute() {
        log.info("test");
    }
}
