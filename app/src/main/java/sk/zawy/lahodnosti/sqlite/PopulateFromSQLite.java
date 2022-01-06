package sk.zawy.lahodnosti.sqlite;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.zawy.lahodnosti.objects.BookItem;
import sk.zawy.lahodnosti.objects.DailyMenu;
import sk.zawy.lahodnosti.objects.Event;
import sk.zawy.lahodnosti.objects.Texts;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_ABOUT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_IMG_URL1;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_IMG_URL2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_IMG_URL3;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_IMG_URL4;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_LOGO_URL;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_NAME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_Q1;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_Q2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_Q3;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_Q4;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_BOOK_Q5;
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
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_MAIN_MEAL2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_MAIN_MEAL3;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_NAME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_NOTIFICATION;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_PHOTO;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_PHOTO2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_PHOTO3;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_SOUP;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_SOUP2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_SOUP3;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_ONLINE_RESERVATION;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PHONE_RESERVATION;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT1;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT3;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT4;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT5;

public class PopulateFromSQLite {
    public static final int POPULATE_EVENTS = 0;
    public static final int POPULATE_BOOK = 1;
    public static final int POPULATE_DAILY_MENU = 2;
    public static final int POPULATE_TEXTS_MAIN = 3;
    public static final int POPULATE_TEXTS_NEW = 4;
    public static final int POPULATE_EVENTS_WHERE_ID = 5;
    public static final int POPULATE_DAILY_MENU_WHERE_ID = 6;
    private List data=null;
    private Map dataMap=null;

    public PopulateFromSQLite(Context context,final int WHAT_WILL_POPULATE) {

        Select select=new Select(context);

        switch (WHAT_WILL_POPULATE){
            case POPULATE_EVENTS:
                data=fillEventsList(select.events());
                select.close();
                break;
            case POPULATE_BOOK:
                data=fillBookList(select.book());
                select.close();
                break;
            case  POPULATE_TEXTS_MAIN:
                data=fillTextList(select.textsMain());
                select.close();
                break;
            case  POPULATE_TEXTS_NEW:
                data=fillTextList(select.textsNew());
                select.close();
                break;

        }
    }

    public PopulateFromSQLite(Context context,final int WHAT_WILL_POPULATE,int arg) {

        Select select=new Select(context);
        select.setArg(arg);
        switch (WHAT_WILL_POPULATE){
            case  POPULATE_DAILY_MENU:
                data=fillDailyMenuList(select.dailyMenu());
                select.close();
                break;
        }
    }


    /** Read data where in(id,id,id...)*/
    public PopulateFromSQLite(Context context,final int WHAT_WILL_POPULATE,String ids) {

        Select select=new Select(context);
        switch (WHAT_WILL_POPULATE){
            case  POPULATE_DAILY_MENU_WHERE_ID:
                data=fillDailyMenuList(select.dailyMenuWhereIdIn(ids));
                select.close();
                break;
            case POPULATE_EVENTS_WHERE_ID:
                data=fillEventsList(select.eventWhereIdIn(ids));
                select.close();
                break;
        }
    }


    private List<Event> fillEventsList(Cursor cursor) {
        data=new ArrayList<Event>();

        while(cursor.moveToNext()){

            Event event=new Event(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_TIME2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_PRICE2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_KIND)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_CAPACITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_INFO_MAIN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    null,
                    null,
                    null,
                    1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ENABLE)),
                    1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ONLINE_RESERVATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_RESERVATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG_URL))
            );

            data.add(event);
        }
        cursor.close();

        return data;
    }


    private List<BookItem> fillBookList(Cursor cursor) {
        data=new ArrayList<BookItem>();
        while(cursor.moveToNext()){
            BookItem bookItem=new BookItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ABOUT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_Q1)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_Q2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_Q3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_Q4)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_Q5)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_LOGO_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL1)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL4)),
                    1,null,null
            );

            data.add(bookItem);
        }
        cursor.close();

        return data;
    }



    private List<DailyMenu> fillDailyMenuList(Cursor cursor) {
        data=new ArrayList<DailyMenu>();
        while(cursor.moveToNext()){
            DailyMenu dailyMenu=new DailyMenu(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_DAY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_SOUP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_MAIN_MEAL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_PHOTO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_SOUP2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_MAIN_MEAL2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_PHOTO2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_SOUP3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_MAIN_MEAL3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_PHOTO3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_NOTIFICATION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EVENT_CAPACITY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EVENT_PRICE)),
                    1,null,null
            );

            data.add(dailyMenu);
        }
        cursor.close();

        return data;
    }



    private List<Texts> fillTextList(Cursor cursor) {
        data=new ArrayList<Texts>();
        while(cursor.moveToNext()){
            Texts texts=new Texts(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXTS_TEXT1)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXTS_TEXT2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXTS_TEXT3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXTS_TEXT4)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXTS_TEXT5)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL1)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMG_URL4)),
                    1,null,null
            );

            data.add(texts);
        }
        cursor.close();

        return data;
    }

    public List getData() {
        return data;
    }

    public Map getDataMap() {
        dataMap=new HashMap();

        for (Object object: data){
            if(object instanceof Event){
                dataMap.put(((Event)object).getId(),object);
                ((Event)object).getId();
            }else if(object instanceof DailyMenu){
                dataMap.put(((DailyMenu)object).getId(),object);
            }
        }
        return dataMap;
    }
}
