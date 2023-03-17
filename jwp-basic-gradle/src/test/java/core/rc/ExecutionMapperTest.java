package core.rc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
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
@ExtendWith(MockitoExtension.class)
class ExecutionMapperTest {

    @InjectMocks
    private ExecutionMapper executionMapper = ExecutionMapper.getInstance();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;


    @Test
    void 매핑_테스트() throws NoSuchMethodException {
        final var sample = Sample.class;

        final var method = sample.getMethod("execute", ArgumentSample.class);

        BDDMockito.given(request.getParameter("a")).willReturn("1");
        BDDMockito.given(request.getParameter("b")).willReturn("2");
        BDDMockito.given(request.getParameter("c")).willReturn("3");

        final var args = executionMapper.doMapping(method, request, response);
        final var result = (ArgumentSample) args[0];

        Assertions.assertThat(result.a).isEqualTo(1);
        Assertions.assertThat(result.b).isEqualTo("2");
        Assertions.assertThat(result.c).isEqualTo('3');


    }


}

class Sample {
    public int execute(@ModelAttribute ArgumentSample argumentSample) {
        return 0;
    }
}

@Getter
class ArgumentSample {
    int a;
    String b;
    char c;

    public ArgumentSample() {
    }
}