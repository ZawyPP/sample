package sk.zawy.lahodnosti.accessories;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.adapters.BookAdapter;
import sk.zawy.lahodnosti.adapters.DailyMenuAdapter;
import sk.zawy.lahodnosti.adapters.EventAdapter;
import sk.zawy.lahodnosti.adapters.FBpostsAdapter;
import sk.zawy.lahodnosti.adapters.RestaurantMainAdapter;
import sk.zawy.lahodnosti.sqlite.PopulateFromSQLite;

public class UseAdapter {

    public UseAdapter(ListView listView , final int WHAT) {
        Context context=listView.getContext();

        List data=new PopulateFromSQLite(context,WHAT).getData();
        ArrayAdapter adapter;
        switch (WHAT) {
            case PopulateFromSQLite.POPULATE_TEXTS_MAIN:
                adapter = new RestaurantMainAdapter(context, R.layout.row_restaurant, data);
                listView.setAdapter(adapter);
                break;
            case PopulateFromSQLite.POPULATE_EVENTS:
                EventAdapter eventAdapter = new EventAdapter(context, R.layout.row_event, data);
                listView.setAdapter(eventAdapter);
                eventAdapter.setListView(listView);
                break;
            case PopulateFromSQLite.POPULATE_TEXTS_NEW:
                adapter = new RestaurantMainAdapter(context, R.layout.row_text, data);
                listView.setAdapter(adapter);
                break;
            case PopulateFromSQLite.POPULATE_BOOK:
                BookAdapter bookAdapter = new BookAdapter(context, R.layout.row_book_logo, data);
                listView.setAdapter(bookAdapter);
                bookAdapter.setListView(listView);
                break;

        }
    }


    public UseAdapter(ListView listView , final int WHAT, int arg) {
        Context context=listView.getContext();

        List data=new PopulateFromSQLite(context,WHAT,arg).getData();
        ArrayAdapter adapter;
        switch (WHAT) {
            case PopulateFromSQLite.POPULATE_DAILY_MENU:
                adapter = new DailyMenuAdapter(context, R.layout.row_daily_menu, data);
                listView.setAdapter(adapter);
                break;

        }
    }

    public UseAdapter(ListView listView , List data) {
        Context context=listView.getContext();

        FBpostsAdapter adapter = new FBpostsAdapter(context, R.layout.row_daily_menu, data);
        listView.setAdapter(adapter);
        adapter.setListView(listView);
        }
}
