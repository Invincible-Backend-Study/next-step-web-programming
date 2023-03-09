package next.qna.ui;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import core.mvc.JspView;
import core.mvc.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.common.error.DomainExceptionCode;
import next.qna.service.DeleteQnaService;
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
    @Mock
    private DeleteQnaService deleteQnaService;

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

    @Test
    void 질문이_정상적으로_작성되지_않으면_다음과_같습니다() throws Exception {
        given(httpRequest.getParameter("questionId")).willReturn("1");

        doThrow(DomainExceptionCode.DID_NOT_DELETE_QUESTION.createError("1")).when(deleteQnaService).execute(1L);
        final var mv = deleteQnaController.execute(httpRequest, httpResponse);

        final View result = new JspView("redirect: /qna/show?questionId=1");

        Assertions.assertThat(mv.getView()).isEqualTo(result);
    }

}