package sk.zawy.lahodnosti.sqlite;

public class StaticValue {

        public static final String DB_NAME = "lahodnosti";
        public static final String TB_EVENTS = "tb_events";
        public static final String TB_BOOK = "tb_book";
        public static  final String TB_MENU = "tb_dailyMenu";
        public static  final String TB_TEXTS = "tb_texts";
        public static  final String TB_PERSONS = "tb_persons";
        public static  final String TB_USERS = "tb_app_users";
        public static  final String TB_TOKEN = "tb_access_point";

        protected static final int DATABASE_VERSION = 1;

        /** EVENTS */
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_EVENT_NAME = "eventName";
        public static final String COLUMN_EVENT_TIME = "time";
        public static final String COLUMN_EVENT_TIME2 = "timeEnd";
        public static final String COLUMN_EVENT_DATE = "date";
        public static final String COLUMN_EVENT_PRICE = "price";
        public static final String COLUMN_EVENT_PRICE2 = "price2";
        public static final String COLUMN_EVENT_KIND = "kind";
        public static final String COLUMN_EVENT_CAPACITY = "capacity";
        public static final String COLUMN_EVENT_INFO = "info";
        public static final String COLUMN_EVENT_INFO_MAIN = "infoMain";
        public static final String COLUMN_PUBLISHED = "published";

        /** PERSONS*/
        public static final String COLUMN_PERSON_COUNT= "count";
        public static final String COLUMN_PERSON_FILLCOUNT="count2";
        public static final String COLUMN_PERSON_ID_EVENT= "event_id";
        public static final String COLUMN_PERSON_NAME ="name";
        public static final String COLUMN_PERSON_PHONE="phone";
        public static final String COLUMN_RESERVATION_KIND="kind";
        public static final String COLUMN_PERSON_INFO="info";
        public static final String COLUMN_PERSON_COST="cost";

        /** BOOK */
        public static final String COLUMN_BOOK_NAME= "name";
        public static final String COLUMN_BOOK_Q1= "question1";
        public static final String COLUMN_BOOK_Q2= "question2";
        public static final String COLUMN_BOOK_Q3= "question3";
        public static final String COLUMN_BOOK_Q4= "question4";
        public static final String COLUMN_BOOK_Q5= "question5";
        public static final String COLUMN_BOOK_ABOUT= "about";
        public static final String COLUMN_BOOK_LOGO_URL= "logo";
        public static final String COLUMN_BOOK_IMG_URL1= "img1";
        public static final String COLUMN_BOOK_IMG_URL2= "img2";
        public static final String COLUMN_BOOK_IMG_URL3= "img3";
        public static final String COLUMN_BOOK_IMG_URL4= "img4";

        /** DAILY MENU */
        public static  final String COLUMN_MENU_NAME = "name";
        public static  final String COLUMN_MENU_DAY  = "daySK";
        public static  final String COLUMN_MENU_SOUP = "soup";
        public static  final String COLUMN_MENU_MAIN_MEAL = "mainMeal";
        public static  final String COLUMN_MENU_PHOTO = "foto";
        public static  final String COLUMN_MENU_SOUP2 = "soup2";
        public static  final String COLUMN_MENU_MAIN_MEAL2 = "mainMeal2";
        public static  final String COLUMN_MENU_PHOTO2 = "foto2";
        public static  final String COLUMN_MENU_SOUP3 = "soup3";
        public static  final String COLUMN_MENU_MAIN_MEAL3 = "mainMeal3";
        public static  final String COLUMN_MENU_PHOTO3 = "foto3";
        public static  final String COLUMN_MENU_NOTIFICATION = "notif";

        /** TEXT TABLE */
        public static  final String COLUMN_TEXTS_TEXT1 = "text1";
        public static  final String COLUMN_TEXTS_TEXT2 = "text2";
        public static  final String COLUMN_TEXTS_TEXT3 = "text3";
        public static  final String COLUMN_TEXTS_TEXT4 = "text4";
        public static  final String COLUMN_TEXTS_TEXT5 = "text5";

        public static final String COLUMN_IMG_URL= "imageURL";
        public static final String COLUMN_ONLINE_RESERVATION ="onlineReservation";
        public static final String COLUMN_PHONE_RESERVATION ="phoneReservation";
        public static final String COLUMN_VISIBILITY="visibility";
        public static final String COLUMN_ENABLE="enable";
        public static final String COLUMN_VIP="vip";
        public static final String COLUMN_CREATED = "created_at";
        public static final String COLUMN_UPDATED = "updated_at";


}



