package nl.pdevos.ikpmd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCreator {
    private SimpleDateFormat dateFormat;

    public DateCreator() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public String getStringFromDate(Date date) {
        return dateFormat.format(date);
    }

    public String getStringFromYearMonthDay(int year, int month, int dayOfMonth) {
        return String.format("%d/%d/%d", dayOfMonth, month, year);
    }

    public String getCurrentDate() {
        Date date = new Date();
        return dateFormat.format(date);
    }
}
