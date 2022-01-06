package sk.zawy.lahodnosti.mySQL;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.sqlite.DatabaseHelper;

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
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_CREATED;
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
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PUBLISHED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT1;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT2;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT3;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT4;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_TEXTS_TEXT5;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_UPDATED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VISIBILITY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_BOOK;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_TEXTS;

public class MySQLtoSQLite {
    private Context context;
    private DatabaseHelper db=null;

    public MySQLtoSQLite(Context context, final String TABLE, ResultSet resultSet) {
        this.context = context;
        db = new DatabaseHelper(context);

        switch (TABLE){
            case TB_EVENTS:
                copyEvents(resultSet);
                break;
            case TB_BOOK:
                copyBook(resultSet);
                break;
            case TB_MENU:
                copyDailyMenu(resultSet);
                break;
            case TB_TEXTS:
                copyTexts(resultSet);
                break;
        }
    }

    private void copyEvents(ResultSet resultSet) {
        ContentValues cv = new ContentValues();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    Log.d("logData", "FROM MYSQL " + TextEdit.notNull(resultSet.getString(COLUMN_EVENT_NAME)));
                    cv.put(COLUMN_ID, resultSet.getString("id"));
                    cv.put(COLUMN_EVENT_NAME, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_NAME)));
                    cv.put(COLUMN_EVENT_TIME, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_TIME)));
                    cv.put(COLUMN_EVENT_TIME2, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_TIME2)));
                    cv.put(COLUMN_EVENT_DATE, TextEdit.notNull(String.valueOf(resultSet.getDate(COLUMN_EVENT_DATE))));
                    cv.put(COLUMN_EVENT_PRICE, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_PRICE)));
                    cv.put(COLUMN_EVENT_PRICE2, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_PRICE2)));
                    cv.put(COLUMN_EVENT_KIND, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_KIND)));
                    cv.put(COLUMN_EVENT_CAPACITY, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_CAPACITY)));
                    cv.put(COLUMN_ONLINE_RESERVATION, resultSet.getInt(COLUMN_ONLINE_RESERVATION));
                    cv.put(COLUMN_PHONE_RESERVATION, TextEdit.notNull(resultSet.getString(COLUMN_PHONE_RESERVATION)));
                    cv.put(COLUMN_EVENT_INFO_MAIN, TextEdit.notNull(resultSet.getString(COLUMN_EVENT_INFO_MAIN)));
                    cv.put(COLUMN_VISIBILITY, resultSet.getInt(COLUMN_VISIBILITY));
                    cv.put(COLUMN_PUBLISHED, resultSet.getInt(COLUMN_PUBLISHED));
                    cv.put(COLUMN_ENABLE, resultSet.getInt(COLUMN_ENABLE));
                    cv.put(COLUMN_CREATED, TextEdit.notNull(resultSet.getString(COLUMN_CREATED)));
                    cv.put(COLUMN_UPDATED, TextEdit.notNull(resultSet.getString(COLUMN_UPDATED)));
                    cv.put(COLUMN_IMG_URL, TextEdit.notNull(resultSet.getString("imageURL")));
                    db.getWritableDatabase().insert(TB_EVENTS, COLUMN_EVENT_NAME, cv);
                    cv.clear();
                }
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        db.close();
    }



    private void copyBook(ResultSet resultSet) {
        ContentValues cv = new ContentValues();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    cv.put(COLUMN_ID, resultSet.getString("id"));
                    cv.put(COLUMN_BOOK_NAME, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_NAME)));
                    cv.put(COLUMN_BOOK_Q1, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_Q1)));
                    cv.put(COLUMN_BOOK_Q2, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_Q2)));
                    cv.put(COLUMN_BOOK_Q3, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_Q3)));
                    cv.put(COLUMN_BOOK_Q4, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_Q4)));
                    cv.put(COLUMN_BOOK_Q5, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_Q5)));
                    cv.put(COLUMN_BOOK_ABOUT, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_ABOUT)));
                    cv.put(COLUMN_BOOK_LOGO_URL, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_LOGO_URL)));
                    cv.put(COLUMN_BOOK_IMG_URL1, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL1)));
                    cv.put(COLUMN_BOOK_IMG_URL2, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL2)));
                    cv.put(COLUMN_BOOK_IMG_URL3, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL3)));
                    cv.put(COLUMN_BOOK_IMG_URL4, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL4)));
                    cv.put(COLUMN_PUBLISHED, resultSet.getInt(COLUMN_PUBLISHED));
                    cv.put(COLUMN_CREATED, TextEdit.notNull(resultSet.getString(COLUMN_CREATED)));
                    cv.put(COLUMN_UPDATED, TextEdit.notNull(resultSet.getString(COLUMN_UPDATED)));
                    db.getWritableDatabase().insert(TB_BOOK, COLUMN_BOOK_NAME, cv);
                    cv.clear();
                }
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        db.close();
    }


    private void copyDailyMenu(ResultSet resultSet) {
        ContentValues cv = new ContentValues();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    cv.put(COLUMN_ID, resultSet.getString("id"));
                    cv.put(COLUMN_MENU_NAME, TextEdit.notNull(resultSet.getString(COLUMN_MENU_NAME)));
                    cv.put(COLUMN_MENU_DAY, TextEdit.notNull(resultSet.getString(COLUMN_MENU_DAY)));
                    cv.put(COLUMN_MENU_SOUP, TextEdit.notNull(resultSet.getString(COLUMN_MENU_SOUP)));
                    cv.put(COLUMN_MENU_MAIN_MEAL, TextEdit.notNull(resultSet.getString(COLUMN_MENU_MAIN_MEAL)));
                    cv.put(COLUMN_MENU_PHOTO, TextEdit.notNull(resultSet.getString(COLUMN_MENU_PHOTO)));
                    cv.put(COLUMN_MENU_SOUP2, TextEdit.notNull(resultSet.getString(COLUMN_MENU_SOUP2)));
                    cv.put(COLUMN_MENU_MAIN_MEAL2, TextEdit.notNull(resultSet.getString(COLUMN_MENU_MAIN_MEAL2)));
                    cv.put(COLUMN_MENU_PHOTO2, TextEdit.notNull(resultSet.getString(COLUMN_MENU_PHOTO2)));
                    cv.put(COLUMN_MENU_SOUP3, TextEdit.notNull(resultSet.getString(COLUMN_MENU_SOUP3)));
                    cv.put(COLUMN_MENU_MAIN_MEAL3, TextEdit.notNull(resultSet.getString(COLUMN_MENU_MAIN_MEAL3)));
                    cv.put(COLUMN_MENU_PHOTO3, TextEdit.notNull(resultSet.getString(COLUMN_MENU_PHOTO3)));
                    cv.put(COLUMN_MENU_NOTIFICATION, TextEdit.notNull(resultSet.getString(COLUMN_MENU_NOTIFICATION)));
                    cv.put(COLUMN_PUBLISHED, resultSet.getInt(COLUMN_PUBLISHED));
                    cv.put(COLUMN_CREATED, TextEdit.notNull(resultSet.getString(COLUMN_CREATED)));
                    cv.put(COLUMN_UPDATED, TextEdit.notNull(resultSet.getString(COLUMN_UPDATED)));
                    db.getWritableDatabase().insert(TB_MENU, COLUMN_BOOK_NAME, cv);
                    cv.clear();
                }
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        db.close();
    }



    private void copyTexts(ResultSet resultSet) {
        ContentValues cv = new ContentValues();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    cv.put(COLUMN_ID, resultSet.getString("id"));
                    cv.put(COLUMN_TEXTS_TEXT1, TextEdit.notNull(resultSet.getString(COLUMN_TEXTS_TEXT1)));
                    cv.put(COLUMN_TEXTS_TEXT2, TextEdit.notNull(resultSet.getString(COLUMN_TEXTS_TEXT2)));
                    cv.put(COLUMN_TEXTS_TEXT3, TextEdit.notNull(resultSet.getString(COLUMN_TEXTS_TEXT3)));
                    cv.put(COLUMN_TEXTS_TEXT4, TextEdit.notNull(resultSet.getString(COLUMN_TEXTS_TEXT4)));
                    cv.put(COLUMN_TEXTS_TEXT5, TextEdit.notNull(resultSet.getString(COLUMN_TEXTS_TEXT5)));
                    cv.put(COLUMN_BOOK_IMG_URL1, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL1)));
                    cv.put(COLUMN_BOOK_IMG_URL2, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL2)));
                    cv.put(COLUMN_BOOK_IMG_URL3, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL3)));
                    cv.put(COLUMN_BOOK_IMG_URL4, TextEdit.notNull(resultSet.getString(COLUMN_BOOK_IMG_URL4)));
                    cv.put(COLUMN_PUBLISHED, resultSet.getInt(COLUMN_PUBLISHED));
                    cv.put(COLUMN_CREATED, TextEdit.notNull(resultSet.getString(COLUMN_CREATED)));
                    cv.put(COLUMN_UPDATED, TextEdit.notNull(resultSet.getString(COLUMN_UPDATED)));
                    db.getWritableDatabase().insert(TB_TEXTS, COLUMN_TEXTS_TEXT1, cv);
                    cv.clear();
                }
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        db.close();
    }

}
