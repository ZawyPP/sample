package sk.zawy.lahodnosti.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.firebase.FirebaseDB;
import sk.zawy.lahodnosti.network.NetworkStatus;
import sk.zawy.lahodnosti.objects.DailyMenu;
import sk.zawy.lahodnosti.objects.Event;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;

public class TaskRealtimeOccupancy extends AsyncTask<Object,Boolean,Void> {
    private Context context;
    private String TABLE;
    private View view;
    private ImageView tickets;
    private TextView reservationStatus;
    private int vacancies;
    private NetworkStatus networkStatus;

    public TaskRealtimeOccupancy(View view, String TABLE) {
        this.context=view.getContext();
        this.view=view;
        this.TABLE=TABLE;
        this.networkStatus=new NetworkStatus(context);
        reservationStatus=view.findViewById(R.id.status);
    }

    /** Spočita kolko osob je nahlasených na udalosť*/
    @Override
    protected Void doInBackground(Object... objects) {
        if(networkStatus.isConnected()) {
            publishProgress(true);

            FirebaseDB db = new FirebaseDB(context);
            int capacity=0;
            switch (TABLE) {
                case TB_EVENTS:
                    Event event = (Event) objects[0];
                    /** Zistí aktualnú obsadenosť danej udalosti*/

                    db.setData(FirebaseDB.COUNT, event.getId(), null);
                    capacity=Integer.parseInt(event.getCapacity());
                    break;
                case TB_MENU:
                    DailyMenu dailyMenu = (DailyMenu) objects[0];
                    /** Zistí aktualnú obsadenosť danej udalosti*/

                    db.setData(FirebaseDB.COUNT, dailyMenu.getId(), null);
                    capacity=dailyMenu.getCapacity();
                    break;
            }

                    try {
                        vacancies = capacity - db.getCount();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }

            publishProgress(false);

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        ViewFlipper viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipperReservation);

        if(networkStatus.isConnected()) {
            SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
            RelativeLayout statusNow=(RelativeLayout)view.findViewById(R.id.nowStatus);
            statusNow.setVisibility(View.VISIBLE);

            if (vacancies <= 0) {
                viewFlipper.setDisplayedChild(2);
                reservationStatus.setText("0");
                reservationStatus.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
            } else if (vacancies > 40) {
               viewFlipper.setDisplayedChild(3);
                reservationStatus.setText("40+");
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else if (vacancies > 20) {
                viewFlipper.setDisplayedChild(3);
                reservationStatus.setText("20+");
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else if (vacancies > 8) {
                viewFlipper.setDisplayedChild(3);
                reservationStatus.setText("8+");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else if (vacancies <= 8) {
                viewFlipper.setDisplayedChild(3);
                reservationStatus.setText(String.valueOf(vacancies));
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                seekBar.setMax(vacancies); //"OCCUPANCY - NASTAVUJEM MAX na " + vacancies
            }
        }else{
            viewFlipper.setDisplayedChild(1);
        }
        super.onPostExecute(aVoid);
    }
}
