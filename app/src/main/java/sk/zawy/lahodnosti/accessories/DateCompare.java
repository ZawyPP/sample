package sk.zawy.lahodnosti.accessories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateCompare {

    private DateFormat dateFormat;
    private Calendar date1;
    private Calendar actual;
    private int hour;
    private int minute;

    public DateCompare(int hour,int minute) {
        this.hour=hour;
        this.minute=minute;
        dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        date1=new GregorianCalendar();
        actual=new GregorianCalendar();
        actual.getInstance();
    }

    public boolean isActual(String date){
        try {
            date1.setTime(dateFormat.parse(date));
            date1.set(Calendar.HOUR_OF_DAY,hour);
            date1.set(Calendar.MINUTE,minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date1.equals(actual)){
            //it is identical
            return false;
        }

        if(date1.after(actual)){
            return true;
        }else {
            return false;
        }

    }

    public boolean isToday(String date){
        Date today=actual.getTime();
        String dateToday= dateFormat.format(today);

        if(date.trim().equals(dateToday)){
            return true;
        }else {
            return false;
        }
    }

}
