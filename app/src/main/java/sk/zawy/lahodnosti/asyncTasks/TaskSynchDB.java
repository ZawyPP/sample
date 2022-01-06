package sk.zawy.lahodnosti.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import sk.zawy.lahodnosti.network.NetworkStatus;
import sk.zawy.lahodnosti.synch.DataForCompare;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_BOOK;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_TEXTS;

public class TaskSynchDB extends AsyncTask<Void,Void,Void> {
    private Context context;

    public TaskSynchDB(Context context) {
        this.context=context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if(new NetworkStatus(context).isConnected()) {

            new DataForCompare(context, TB_EVENTS);
            new DataForCompare(context, TB_BOOK);
            new DataForCompare(context, TB_MENU);
            new DataForCompare(context, TB_TEXTS);

        }
        return null;
    }
}
