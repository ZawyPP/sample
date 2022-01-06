package sk.zawy.lahodnosti.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sk.zawy.lahodnosti.R;

import static sk.zawy.lahodnosti.sqlite.StaticValue.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TB_EVENTS + "(" +
                COLUMN_ID + " VARCHAR PRIMARY KEY, " +
                COLUMN_EVENT_NAME + " VARCHAR NOT NULL, " +
                COLUMN_EVENT_TIME + " VARCHAR DEFAULT NULL, " +
                COLUMN_EVENT_TIME2 + " VARCHAR DEFAULT NULL, " +
                COLUMN_EVENT_DATE + " DATE DEFAULT NULL, " +
                COLUMN_EVENT_PRICE + " VARCHAR DEFAULT \"0\", " +
                COLUMN_EVENT_PRICE2 + " VARCHAR DEFAULT NULL, " +
                COLUMN_EVENT_KIND + " VARCHAR DEFAULT NULL, " +
                COLUMN_IMG_URL + " VARCHAR DEFAULT NULL, " +
                COLUMN_VISIBILITY + " INTEGER DEFAULT 1, " +
                COLUMN_ENABLE + " INTEGER DEFAULT 1, " +
                COLUMN_PUBLISHED  + " INTEGER DEFAULT 0, " +
                COLUMN_EVENT_CAPACITY + " VARCHAR DEFAULT 0, " +
                COLUMN_EVENT_INFO_MAIN + " TEXT DEFAULT NULL, " +
                COLUMN_PHONE_RESERVATION + " VARCHAR DEFAULT \"" + context.getString(R.string.phone) + "\"," +
                COLUMN_ONLINE_RESERVATION + " INTEGER DEFAULT 0, " +
                COLUMN_VIP + " INTEGER DEFAULT 0, " +
                COLUMN_CREATED + " TIMESTAMP DEFAULT (datetime('now','localtime')), " +
                COLUMN_UPDATED + " TIMESTAMP DEFAULT NULL " +
                ");");

        db.execSQL("CREATE TABLE " + TB_BOOK + "(" +
                COLUMN_ID + " VARCHAR PRIMARY KEY, " +
                COLUMN_BOOK_NAME + " VARCHAR NOT NULL, " +
                COLUMN_BOOK_Q1 + " TEXT DEFAULT NULL, " +
                COLUMN_BOOK_Q2 + " TEXT DEFAULT NULL, " +
                COLUMN_BOOK_Q3 + " TEXT DEFAULT NULL, " +
                COLUMN_BOOK_Q4 + " TEXT DEFAULT NULL, " +
                COLUMN_BOOK_Q5 + " TEXT DEFAULT NULL, " +
                COLUMN_BOOK_ABOUT + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_LOGO_URL + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL1 + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL2 + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL3 + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL4 + " VARCHAR DEFAULT NULL, " +
                COLUMN_PUBLISHED  + " INTEGER DEFAULT 0, " +
                COLUMN_CREATED + " TIMESTAMP DEFAULT (datetime('now','localtime')), " +
                COLUMN_UPDATED + " TIMESTAMP DEFAULT NULL " +
                ");");

        db.execSQL("CREATE TABLE " + TB_MENU + "(" +
                COLUMN_ID + " DATE PRIMARY KEY, " +
                COLUMN_MENU_NAME + " TEXT DEFAULT NULL, " +
                COLUMN_MENU_DAY + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_SOUP + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_MAIN_MEAL + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_PHOTO + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_SOUP2 + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_MAIN_MEAL2 + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_PHOTO2 + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_SOUP3 + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_MAIN_MEAL3 + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_PHOTO3 + " VARCHAR DEFAULT NULL, " +
                COLUMN_MENU_NOTIFICATION + " TEXT DEFAULT NULL, " +
                COLUMN_PUBLISHED  + " INTEGER DEFAULT 0, " +
                COLUMN_EVENT_PRICE  + " DOUBLE DEFAULT 0, " +
                COLUMN_EVENT_CAPACITY + " INTEGER DEFAULT 0, " +
                COLUMN_CREATED + " TIMESTAMP DEFAULT (datetime('now','localtime')), " +
                COLUMN_UPDATED + " TIMESTAMP DEFAULT NULL " +
                ");");


        db.execSQL("CREATE TABLE " + TB_TEXTS + "(" +
                COLUMN_ID + " VARCHAR PRIMARY KEY, " +
                COLUMN_TEXTS_TEXT1 + " TEXT DEFAULT NULL, " +
                COLUMN_TEXTS_TEXT2 + " TEXT DEFAULT NULL, " +
                COLUMN_TEXTS_TEXT3 + " TEXT DEFAULT NULL, " +
                COLUMN_TEXTS_TEXT4 + " TEXT DEFAULT NULL, " +
                COLUMN_TEXTS_TEXT5 + " TEXT DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL1 + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL2 + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL3 + " VARCHAR DEFAULT NULL, " +
                COLUMN_BOOK_IMG_URL4 + " VARCHAR DEFAULT NULL, " +
                COLUMN_PUBLISHED  + " INTEGER DEFAULT 0, " +
                COLUMN_CREATED + " TIMESTAMP DEFAULT (datetime('now','localtime')), " +
                COLUMN_UPDATED + " TIMESTAMP DEFAULT NULL " +
                ");");


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
