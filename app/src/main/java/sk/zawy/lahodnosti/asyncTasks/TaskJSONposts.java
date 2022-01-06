package sk.zawy.lahodnosti.asyncTasks;

import android.os.AsyncTask;

import java.util.List;

import sk.zawy.lahodnosti.activities.MainScreen;
import sk.zawy.lahodnosti.facebookPosts.JSONfromFB;
import sk.zawy.lahodnosti.network.NetworkStatus;

public class TaskJSONposts  extends AsyncTask<Object,Boolean, List> {

    private MainScreen activity;

    public TaskJSONposts(MainScreen activity) {
        this.activity=activity;
    }

    @Override
    protected List doInBackground(Object... objects) {
        if (new NetworkStatus(activity).isConnected()) {
            try {
                return new JSONfromFB().connectFB();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Boolean... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List data) {
        super.onPostExecute(data);

        activity.dataForAdapter(data);
    }
}

