package com.jwp.outbound.user.ui;

import com.jwp.inbound.user.port.driving.ListUserUseCase;
import com.jwp.inbound.user.port.driving.dto.ReadOnlyUser;
import core.mvc.ModelAndView;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith({MockitoExtension.class})
class UserEntityPresentationTest {

    @InjectMocks
    private UserPresentation userPresentation;

    @Mock
    private ListUserUseCase listUserUseCase;

    @Test
    void 사용자_프레젠테이션계층에서_전체_사용자_목록을_랜더링합니다() {
        final var userList = List.of(ReadOnlyUser.of("1234", "1234", "1234"));
        BDDMockito.given(listUserUseCase.execute()).willReturn(userList);

        final var result = userPresentation.renderAllUser();
        final var resultView = result.getView();

        Assertions.assertThat(resultView).isEqualTo(ModelAndView.renderPage("user/list.jsp").getView());

    }

}