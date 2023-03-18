package bookexample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import org.junit.jupiter.api.Test;

class DateMessageProviderTest {

    @Test
    void 오전() {
        // given
        DateMessageProvider provider = new DateMessageProvider();

        // then
        assertThat(provider.getDateMessage(createCurrentDate(11))).isEqualTo("오전");
    }

    @Test
    void 오후() {
        // given
        DateMessageProvider provider = new DateMessageProvider();

        // then
        assertThat(provider.getDateMessage(createCurrentDate(13))).isEqualTo("오후");
    }

    private Calendar createCurrentDate(final int hourOfDay) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, hourOfDay);
        return now;
    }

}