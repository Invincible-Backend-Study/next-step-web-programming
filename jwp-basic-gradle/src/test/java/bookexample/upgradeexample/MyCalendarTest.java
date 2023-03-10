package bookexample.upgradeexample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import org.junit.jupiter.api.Test;

public class MyCalendarTest {

    @Test
    void getHour() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 11);
        MyCalendar myCalendar = new MyCalendar(now);
        assertThat(myCalendar.getHour()).isEqualTo(new Hour(11));
    }
}
