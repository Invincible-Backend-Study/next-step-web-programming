package next.qna.ui;

import static org.mockito.BDDMockito.given;

import core.mvc.JspView;
import core.mvc.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class DeleteQnaControllerTest {

    @InjectMocks
    private DeleteQnaController deleteQnaController;

    @Mock
    private HttpServletRequest httpRequest;
    @Mock
    private HttpServletResponse httpResponse;

    @Test
    public void test() throws Exception {
        given(httpRequest.getParameter("questionId")).willReturn("1");
        deleteQnaController.execute(httpRequest, httpResponse);
    }

    @Test
    void 질문이존재하지_않는경우_리다이렉트_url_은_index이다() throws Exception {
        given(httpRequest.getParameter("questionId")).willReturn(null);
        final var mv = deleteQnaController.execute(httpRequest, httpResponse);

        final View result = new JspView("redirect: /");

        Assertions.assertThat(mv.getView()).isEqualTo(result);

    }

}