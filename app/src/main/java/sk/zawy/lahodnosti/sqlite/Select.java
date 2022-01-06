package sk.zawy.lahodnosti.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import sk.zawy.lahodnosti.login.Profil;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_ENABLE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_CAPACITY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_DATE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_INFO_MAIN;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_KIND;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_NAME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_PRICE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_PRICE2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_TIME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_TIME2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_ID;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_IMG_URL;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_DAY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_MAIN_MEAL;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_SOUP;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_ONLINE_RESERVATION;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PHONE_RESERVATION;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PUBLISHED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_UPDATED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VIP;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VISIBILITY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_BOOK;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_TEXTS;

public class Select {
    private DatabaseHelper db;

    private String[]arg=null;
    public Select(Context context) {
        db=new DatabaseHelper(context);
    }


    /**select events from SQLite
     * @return
     * All events where isVisibility(no removed)
     * and version have status "Public"
     * and are intended for the public (all)  */
    public Cursor events(){

        if(Profil.getInstance().isVip()){
            return db.getReadableDatabase().rawQuery(
            "SELECT " +
                        COLUMN_ID + " , " +
                        COLUMN_EVENT_NAME + " , " +
                        COLUMN_EVENT_TIME + " , " +
                        COLUMN_EVENT_TIME2 + " , " +
                        COLUMN_EVENT_DATE + " , " +
                        COLUMN_EVENT_PRICE + " , " +
                        COLUMN_EVENT_PRICE2 + " , " +
                        COLUMN_EVENT_KIND + " , " +
                        COLUMN_IMG_URL + " , " +
                        COLUMN_ENABLE + " , " +
                        COLUMN_EVENT_CAPACITY + " , " +
                        COLUMN_EVENT_INFO_MAIN + " , " +
                        COLUMN_PHONE_RESERVATION + " , " +
                        COLUMN_ONLINE_RESERVATION +
                        " FROM " + TB_EVENTS + " WHERE "+ COLUMN_PUBLISHED + " = 1 " +
                        " AND " + COLUMN_VISIBILITY + " =  1 " +
                        " ORDER BY " + COLUMN_EVENT_DATE +
                        " DESC",null);
        }else{
            return db.getReadableDatabase().rawQuery(
                    "SELECT " +
                            COLUMN_ID + " , " +
                            COLUMN_EVENT_NAME + " , " +
                            COLUMN_EVENT_TIME + " , " +
                            COLUMN_EVENT_TIME2 + " , " +
                            COLUMN_EVENT_DATE + " , " +
                            COLUMN_EVENT_PRICE + " , " +
                            COLUMN_EVENT_PRICE2 + " , " +
                            COLUMN_EVENT_KIND + " , " +
                            COLUMN_IMG_URL + " , " +
                            COLUMN_ENABLE + " , " +
                            COLUMN_EVENT_CAPACITY + " , " +
                            COLUMN_EVENT_INFO_MAIN + " , " +
                            COLUMN_PHONE_RESERVATION + " , " +
                            COLUMN_ONLINE_RESERVATION +
                            " FROM " + TB_EVENTS + " WHERE "+ COLUMN_PUBLISHED + " = 1 " +
                            " AND " + COLUMN_VISIBILITY + " =  1 " +
                            " AND " + COLUMN_VIP + " = 0 " +
                            " ORDER BY " + COLUMN_EVENT_DATE +
                            " DESC",null);

        }
    }


    public Cursor event(String id){
        return db.getReadableDatabase().rawQuery(
                "SELECT " +
                        COLUMN_EVENT_NAME + " , " +
                        COLUMN_EVENT_TIME + " , " +
                        COLUMN_EVENT_TIME2 + " , " +
                        COLUMN_EVENT_DATE + " , " +
                        COLUMN_EVENT_KIND +
                        " FROM " + TB_EVENTS + " WHERE "+ COLUMN_ID + " = ? ",new String[]{id});
    }

    public Cursor eventWhereIdIn(String id) {
        return db.getReadableDatabase()
                .rawQuery("SELECT * FROM " + TB_EVENTS + " WHERE " + COLUMN_ID + " IN ( " + id + " )",null);
    }

    public Cursor dailyMenuWhereIdIn(String id) {
        return db.getReadableDatabase()
                .rawQuery("SELECT * FROM " + TB_MENU + " WHERE " + COLUMN_ID + " IN ( " + id + " )",null);
    }

    public Cursor dailyMenu(String id){
        return db.getReadableDatabase().rawQuery(
                "SELECT " +
                        COLUMN_MENU_SOUP + " , " +
                        COLUMN_MENU_MAIN_MEAL + " , " +
                        COLUMN_MENU_DAY + " , " +
                        COLUMN_ID + " " +
                        " FROM " + TB_MENU + " WHERE "+ COLUMN_ID + " = ? ",new String[]{id});
    }

    public Cursor book(){
        return db.getReadableDatabase().rawQuery(
                "SELECT * " +
                        " FROM " + TB_BOOK ,null);
    }

    public Cursor dailyMenu(){
        return db.getReadableDatabase().rawQuery(
                "SELECT * " +
                        " FROM " + TB_MENU + " WHERE DATE(" + COLUMN_ID + " ) > DATE('now', 'weekday 0', ?) " +
                        "AND DATE(" + COLUMN_ID + " )  < DATE('now', 'weekday 0', ?) " +
                        " AND " + COLUMN_PUBLISHED + " = 1 " +
                        "order by " + COLUMN_ID
                ,getArg());
    }

    public Cursor countDailyMenu(){
        return db.getReadableDatabase().rawQuery(
                "SELECT COUNT(*) " +
                        " FROM " + TB_MENU + " WHERE DATE(" + COLUMN_ID + " ) > DATE('now', 'weekday 0', ?) " +
                        "AND DATE(" + COLUMN_ID + " )  < DATE('now', 'weekday 0', ?) " +
                        "AND "+ COLUMN_PUBLISHED + " = 1  order by " + COLUMN_ID
                ,getArg());
    }

    public Cursor dataMap(String TABLE){
        return db.getReadableDatabase().rawQuery(
                "SELECT " + COLUMN_ID + ", " +
                COLUMN_UPDATED + " FROM " + TABLE,null);
    }

    public Cursor textsMain() {
        return db.getReadableDatabase().rawQuery(
                "SELECT * " +
                        " FROM " + TB_TEXTS + " WHERE " + COLUMN_ID +
                " = 1 ",null);
    }

    public Cursor textsNew() {
        return db.getReadableDatabase().rawQuery(
                "SELECT * " +
                        " FROM " + TB_TEXTS + " WHERE " + COLUMN_ID +
                        " != 1 ",null);
    }



    public void close(){
        db.close();
    }


    public void setArg(int arg) {

        int start= -7;
        int end= 1;

        if(arg!=0) {
            for (int x = 0; x != arg; ) {
                if (arg < 0) {
                    start -= 7;
                    end -= 7;
                    x--;
                } else if (arg > 0) {
                    start += 7;
                    end += 7;
                    x++;
                }
            }
        }

        this.arg=new String []{String.valueOf(start + " days"),String.valueOf(end + " days")};

    }

    public String[] getArg() {
        return arg;
    }
}
