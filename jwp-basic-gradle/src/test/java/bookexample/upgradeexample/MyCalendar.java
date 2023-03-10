package bookexample.upgradeexample;

import java.util.Calendar;

public class MyCalendar {
    private final Calendar calendar;

    public MyCalendar(final Calendar calendar) {
        this.calendar = calendar;
    }

    public Hour getHour() {
        return new Hour(calendar.get(Calendar.HOUR_OF_DAY));
    }

}
