package core.rc.filter;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ExecutionFilterScannerTest {


    @Test
    void 테스트() {
        final var result = new ExecutionFilterScanner("core.rc.filter").getExecutionFilter();

        Assertions.assertThat(result).hasSize(3);

        Assertions.assertThat(result.get(0)).isInstanceOf(FilterB.class);
        Assertions.assertThat(result.get(1)).isInstanceOf(FilterA.class);
        Assertions.assertThat(result.get(2)).isInstanceOf(FilterC.class);

    }

}