package bookexample.upgradeexample;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HourTest {

    @Test
    void 오전() {
        Hour hour = new Hour(11);
        assertThat(hour.getMessage()).isEqualTo("오전");
    }

    @Test
    void 오후() {
        Hour hour = new Hour(12);
        assertThat(hour.getMessage()).isEqualTo("오후");
    }
}
