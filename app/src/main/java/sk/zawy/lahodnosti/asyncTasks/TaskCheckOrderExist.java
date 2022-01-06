package sk.zawy.lahodnosti.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.firebase.FirebaseDB;
import sk.zawy.lahodnosti.holder.ViewHolderOnlineReservation;
import sk.zawy.lahodnosti.holder.ViewHolderOrderDailyMenuOptions;
import sk.zawy.lahodnosti.login.Profil;
import sk.zawy.lahodnosti.network.NetworkStatus;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_COUNT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_ID_EVENT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_INFO;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_NAME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_PHONE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_PERSONS;

public class TaskCheckOrderExist extends AsyncTask<Object,Boolean,Map<String,Object>> {
    private Context context;
    private String TABLE;
    private View view;
    private View view2;
    private ViewHolderOnlineReservation viewHolder;
    private ProgressDialog pd;

    public TaskCheckOrderExist(View view, View view2, String TABLE) {
        this.context=view.getContext();
        this.view=view;
        this.view2=view2;
        this.TABLE=TABLE;
        this.viewHolder=new ViewHolderOnlineReservation(view);
        this.pd=new ProgressDialog(context);
    }

    /** Spočita kolko osob je nahlasených na udalosť
     * @param objects == id event/dailyMenu*/
    @Override
    protected Map<String,Object> doInBackground(Object... objects) {

        if(new NetworkStatus(context).isConnected()) {
            publishProgress(true);

            String id = ((String) objects[0]) + Profil.getInstance().getEmail();
            List<String> ids = new ArrayList<>();
            ids.add(id);

            FirebaseDB db = new FirebaseDB(context);
            db.setData(FirebaseDB.READ_ALL, ids, TB_PERSONS);

            if (db.getResult().size() == 0) {
                publishProgress(false);
                return null;
            }

            publishProgress(false);

            return db.getResult();

        }else{
            return null;
        }

    }


    @Override
    protected void onProgressUpdate(Boolean... values) {

        if (values[0]){
            //todo progress
        }else{

        }

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Map<String,Object> result) {

        if(result!=null) {

                int count = 0;
                try {
                    count = Integer.parseInt((String) result.get(COLUMN_PERSON_COUNT));
                } catch (Exception e) {
                    count = 0;
                }

                if(count!=0) {
                    viewHolder.cancelButton.setVisibility(View.VISIBLE);
                    viewHolder.textForButton.setVisibility(View.VISIBLE);

                    String info=(String) result.get(COLUMN_PERSON_INFO);
                    viewHolder.info.setText(info);
                    viewHolder.count.setText(String.valueOf(count));

                    if(viewHolder.seekBar.getMax()+count <8){
                        viewHolder.seekBar.setMax(viewHolder.seekBar.getMax()+count);
                    }else{
                        viewHolder.seekBar.setMax(8);
                    }

                    viewHolder.seekBar.setProgress(count);

                    viewHolder.actualOrderCount.setText(String.valueOf((String) result.get(COLUMN_PERSON_COUNT)));


                    if(info.contains("►")&&info.contains("◄")){
                        if(((String) result.get(COLUMN_PERSON_ID_EVENT)).contains("-")){
                            viewHolder.special.setChecked(true);

                            ViewHolderOrderDailyMenuOptions holderOprions = new ViewHolderOrderDailyMenuOptions(view2);
                            holderOprions.setOptions(result);
                        }
                    }
                }
        }
    }
}
