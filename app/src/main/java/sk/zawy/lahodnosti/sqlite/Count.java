package sk.zawy.lahodnosti.sqlite;

import android.content.Context;
import android.database.Cursor;

public class Count {
    public static final int COUNT_DAILY_MENU=0;
    private Cursor cursor;
    private int count;

    public Count(Context context,int WHAT,int week) {

        switch (WHAT){
            case COUNT_DAILY_MENU:
                Select select=new Select(context);
                select.setArg(week);
                cursor=select.countDailyMenu();
                break;
        }
    }

    public int getCount() {
        while(cursor.moveToNext()){
           count = Integer.parseInt(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));
        }
        return count;
    }

}
