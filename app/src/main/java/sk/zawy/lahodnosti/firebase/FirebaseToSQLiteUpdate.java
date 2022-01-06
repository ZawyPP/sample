package sk.zawy.lahodnosti.firebase;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.Map;

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
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VIP;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VISIBILITY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_BOOK;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_TEXTS;

public class FirebaseToSQLiteUpdate {
    private Context context;
    private DatabaseHelper db=null;

    public FirebaseToSQLiteUpdate(Context context, final String TABLE, Map object) {
        this.context = context;
        db = new DatabaseHelper(context);

        switch (TABLE){
            case TB_EVENTS:
                updateEvents(object);
                break;
            case TB_BOOK:
                updateBook(object);
                break;
            case TB_MENU:
                updateDailyMenu(object);
                break;
            case TB_TEXTS:
                updateTexts(object);
                break;
        }
    }

    private void updateEvents(Map <String,Object>mapObjects) {
        ContentValues cv = new ContentValues();
        Map<String,Object> map=null;
        for(Map.Entry mapObject : mapObjects.entrySet()){

            map =(Map<String, Object>) mapObject.getValue();
            cv.put(COLUMN_EVENT_NAME, TextEdit.notNull((String)map.get(COLUMN_EVENT_NAME)));
            cv.put(COLUMN_EVENT_TIME, TextEdit.notNull((String)map.get(COLUMN_EVENT_TIME)));
            cv.put(COLUMN_EVENT_TIME2, TextEdit.notNull((String)map.get(COLUMN_EVENT_TIME2)));
            cv.put(COLUMN_EVENT_DATE, TextEdit.notNull((String) (map.get(COLUMN_EVENT_DATE))));
            cv.put(COLUMN_EVENT_PRICE, TextEdit.notNull((String)map.get(COLUMN_EVENT_PRICE)));
            cv.put(COLUMN_EVENT_PRICE2, TextEdit.notNull((String)map.get(COLUMN_EVENT_PRICE2)));
            cv.put(COLUMN_EVENT_KIND, TextEdit.notNull((String)map.get(COLUMN_EVENT_KIND)));
            cv.put(COLUMN_EVENT_CAPACITY, TextEdit.notNull((String)map.get(COLUMN_EVENT_CAPACITY)));
            cv.put(COLUMN_ONLINE_RESERVATION, String.valueOf(map.get(COLUMN_ONLINE_RESERVATION)));
            cv.put(COLUMN_PHONE_RESERVATION, TextEdit.notNull((String)map.get(COLUMN_PHONE_RESERVATION)));
            cv.put(COLUMN_EVENT_INFO_MAIN, TextEdit.notNull((String)map.get(COLUMN_EVENT_INFO_MAIN)));
            cv.put(COLUMN_VISIBILITY, String.valueOf(map.get(COLUMN_VISIBILITY)));
            cv.put(COLUMN_PUBLISHED, String.valueOf(map.get(COLUMN_PUBLISHED)));
            cv.put(COLUMN_ENABLE, String.valueOf(map.get(COLUMN_ENABLE)));
            cv.put(COLUMN_VIP, String.valueOf(map.get(COLUMN_VIP)));
            cv.put(COLUMN_CREATED, TextEdit.notNull((String)map.get(COLUMN_CREATED)));
            cv.put(COLUMN_UPDATED, TextEdit.notNull((String)map.get(COLUMN_UPDATED)));
            cv.put(COLUMN_IMG_URL, TextEdit.notNull((String)map.get(COLUMN_IMG_URL)));
            db.getWritableDatabase().update(TB_EVENTS, cv, COLUMN_ID + " = ? ", new String[]{(String)map.get(COLUMN_ID)});

            map.clear();
            cv.clear();

        }

        db.close();
    }

    private void updateBook(Map <String,Object>mapObjects) {
        ContentValues cv = new ContentValues();
        Map<String,Object> map=null;
        for(Map.Entry mapObject : mapObjects.entrySet()){

            map =(Map<String, Object>) mapObject.getValue();
            cv.put(COLUMN_BOOK_NAME, TextEdit.notNull((String) map.get(COLUMN_BOOK_NAME)));
            cv.put(COLUMN_BOOK_Q1, TextEdit.notNull((String) map.get(COLUMN_BOOK_Q1)));
            cv.put(COLUMN_BOOK_Q2, TextEdit.notNull((String) map.get(COLUMN_BOOK_Q2)));
            cv.put(COLUMN_BOOK_Q3, TextEdit.notNull((String) map.get(COLUMN_BOOK_Q3)));
            cv.put(COLUMN_BOOK_Q4, TextEdit.notNull((String) map.get(COLUMN_BOOK_Q4)));
            cv.put(COLUMN_BOOK_Q5, TextEdit.notNull((String) map.get(COLUMN_BOOK_Q5)));
            cv.put(COLUMN_BOOK_ABOUT, TextEdit.notNull((String) map.get(COLUMN_BOOK_ABOUT)));
            cv.put(COLUMN_BOOK_LOGO_URL, TextEdit.notNull((String) map.get(COLUMN_BOOK_LOGO_URL)));
            cv.put(COLUMN_BOOK_IMG_URL1, TextEdit.notNull((String) map.get(COLUMN_BOOK_IMG_URL1)));
            cv.put(COLUMN_BOOK_IMG_URL2, TextEdit.notNull((String) map.get(COLUMN_BOOK_IMG_URL2)));
            cv.put(COLUMN_BOOK_IMG_URL3, TextEdit.notNull((String) map.get(COLUMN_BOOK_IMG_URL3)));
            cv.put(COLUMN_BOOK_IMG_URL4, TextEdit.notNull((String) map.get(COLUMN_BOOK_IMG_URL4)));
            cv.put(COLUMN_PUBLISHED, String.valueOf(map.get(COLUMN_PUBLISHED)));
            cv.put(COLUMN_CREATED, TextEdit.notNull((String) map.get(COLUMN_CREATED)));
            cv.put(COLUMN_UPDATED, TextEdit.notNull((String) map.get(COLUMN_UPDATED)));
            db.getWritableDatabase().update(TB_BOOK, cv, COLUMN_ID + " = ? ", new String[]{(String) map.get(COLUMN_ID)});
            map.clear();
            cv.clear();
        }

        db.close();
    }

    private void updateDailyMenu(Map <String,Object>mapObjects) {
        ContentValues cv = new ContentValues();
        Map<String,Object> map=null;
        for(Map.Entry mapObject : mapObjects.entrySet()){

            map =(Map<String, Object>) mapObject.getValue();
            cv.put(COLUMN_ID, String.valueOf((String)map.get(COLUMN_ID)));
            cv.put(COLUMN_MENU_NAME, TextEdit.notNull((String)map.get(COLUMN_MENU_NAME)));
            cv.put(COLUMN_MENU_DAY, TextEdit.notNull((String)map.get(COLUMN_MENU_DAY)));
            cv.put(COLUMN_MENU_SOUP, TextEdit.notNull((String)map.get(COLUMN_MENU_SOUP)));
            cv.put(COLUMN_MENU_MAIN_MEAL, TextEdit.notNull((String)map.get(COLUMN_MENU_MAIN_MEAL)));
            cv.put(COLUMN_MENU_PHOTO, TextEdit.notNull((String)map.get(COLUMN_MENU_PHOTO)));
            cv.put(COLUMN_MENU_SOUP2, TextEdit.notNull((String)map.get(COLUMN_MENU_SOUP2)));
            cv.put(COLUMN_MENU_MAIN_MEAL2, TextEdit.notNull((String)map.get(COLUMN_MENU_MAIN_MEAL2)));
            cv.put(COLUMN_MENU_PHOTO2, TextEdit.notNull((String)map.get(COLUMN_MENU_PHOTO2)));
            cv.put(COLUMN_MENU_SOUP3, TextEdit.notNull((String)map.get(COLUMN_MENU_SOUP3)));
            cv.put(COLUMN_MENU_MAIN_MEAL3, TextEdit.notNull((String)map.get(COLUMN_MENU_MAIN_MEAL3)));
            cv.put(COLUMN_MENU_PHOTO3, TextEdit.notNull((String)map.get(COLUMN_MENU_PHOTO3)));
            cv.put(COLUMN_MENU_NOTIFICATION, TextEdit.notNull((String)map.get(COLUMN_MENU_NOTIFICATION)));
            cv.put(COLUMN_PUBLISHED, String.valueOf(map.get(COLUMN_PUBLISHED)));
            cv.put(COLUMN_EVENT_CAPACITY, String.valueOf(map.get(COLUMN_EVENT_CAPACITY)));
            cv.put(COLUMN_EVENT_PRICE, String.valueOf(map.get(COLUMN_EVENT_PRICE)));
            cv.put(COLUMN_CREATED, TextEdit.notNull((String)map.get(COLUMN_CREATED)));
            cv.put(COLUMN_UPDATED, TextEdit.notNull((String)map.get(COLUMN_UPDATED)));
            db.getWritableDatabase().update(TB_MENU, cv, COLUMN_ID + " = ? ", new String[]{(String)map.get(COLUMN_ID)});
            map.clear();
            cv.clear();
        }

        db.close();
    }


    private void updateTexts(Map <String,Object>mapObjects) {
        ContentValues cv = new ContentValues();
        Map<String,Object> map=null;
        for(Map.Entry mapObject : mapObjects.entrySet()){

            map =(Map<String, Object>) mapObject.getValue();
            cv.put(COLUMN_ID, (String)map.get(COLUMN_ID));
            cv.put(COLUMN_TEXTS_TEXT1, TextEdit.notNull((String)map.get(COLUMN_TEXTS_TEXT1)));
            cv.put(COLUMN_TEXTS_TEXT2, TextEdit.notNull((String)map.get(COLUMN_TEXTS_TEXT2)));
            cv.put(COLUMN_TEXTS_TEXT3, TextEdit.notNull((String)map.get(COLUMN_TEXTS_TEXT3)));
            cv.put(COLUMN_TEXTS_TEXT4, TextEdit.notNull((String)map.get(COLUMN_TEXTS_TEXT4)));
            cv.put(COLUMN_TEXTS_TEXT5, TextEdit.notNull((String)map.get(COLUMN_TEXTS_TEXT5)));
            cv.put(COLUMN_BOOK_IMG_URL1, TextEdit.notNull((String)map.get(COLUMN_BOOK_IMG_URL1)));
            cv.put(COLUMN_BOOK_IMG_URL2, TextEdit.notNull((String)map.get(COLUMN_BOOK_IMG_URL2)));
            cv.put(COLUMN_BOOK_IMG_URL3, TextEdit.notNull((String)map.get(COLUMN_BOOK_IMG_URL3)));
            cv.put(COLUMN_BOOK_IMG_URL4, TextEdit.notNull((String)map.get(COLUMN_BOOK_IMG_URL4)));
            cv.put(COLUMN_PUBLISHED, String.valueOf(map.get(COLUMN_PUBLISHED)));
            cv.put(COLUMN_CREATED, TextEdit.notNull((String)map.get(COLUMN_CREATED)));
            cv.put(COLUMN_UPDATED, TextEdit.notNull((String)map.get(COLUMN_UPDATED)));
            db.getWritableDatabase().update(TB_TEXTS, cv, COLUMN_ID + " = ? ", new String[]{(String)map.get(COLUMN_ID)});
            map.clear();
            cv.clear();
        }

        db.close();
    }


}
