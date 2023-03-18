package bookexample;

import java.util.Calendar;

public class DateMessageProvider {

    public String getDateMessage(final Calendar now) {
        int hour = now.get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            return "오전";
        }
        return "오후";
    }
}
