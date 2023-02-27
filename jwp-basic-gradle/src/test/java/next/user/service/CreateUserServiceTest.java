package next.user.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import next.common.error.DomainException;
import next.common.error.DomainExceptionCode;
import next.dao.template.DataTest;
import next.user.payload.request.CreateUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class CreateUserServiceTest extends DataTest { //database와 연결되어 있음
    private static volatile Throwable exceptionThread = null;

    private final CreateUserService createUserService = new CreateUserService();

    @Test
    void 중복된_사용자가_발생하면_에러를_던지도록_구현() {
        assertThatThrownBy(() -> {
            createUserService.execute(this.generateCreateUserDto("id"));
            createUserService.execute(this.generateCreateUserDto("id"));
        }).isInstanceOf(DomainException.class)
                .hasMessageContaining(DomainExceptionCode.PREVIOUS_SIGN_UP_USER_ID.getMessage("id"));
    }

    private CreateUserDto generateCreateUserDto(String id) {
        return new CreateUserDto(
                id,
                "password",
                "name",
                "email"
        );
    }

    @Test
    void 동시에_사용자_생성을_요청했을때_정상적으로_에러를_뱉어내는지_검증하는_테스트_코드_작성() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        final var THREAD_COUNT = 40;
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                try {
                    this.createUserService.execute(this.generateCreateUserDto("id0"));
                } catch (DomainException domainException) {
                    // do not
                } catch (Exception exception) {
                    exceptionThread = exception;
                }
            });
            threads.add(thread);
        }

        // 스레드 시작
        for (Thread thread : threads) {
            thread.start();
        }

        // 모든 스레드 종료 대기
        for (Thread thread : threads) {
            thread.join();
        }

        Assertions.assertThat(exceptionThread).isNull();
    }

}