package sk.zawy.lahodnosti.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.animate.ViewSetAlpha;
import sk.zawy.lahodnosti.mySQL.Sum;
import sk.zawy.lahodnosti.network.NetworkStatus;
import sk.zawy.lahodnosti.objects.Event;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;

public class TaskMySqlSUM extends AsyncTask<Object,Boolean,Void> {
    private Context context;
    private String TABLE;
    private View view;
    private ImageView tickets;
    private TextView reservationStatus;
    private int vacancies;
    private NetworkStatus networkStatus;

    public TaskMySqlSUM(View view, String TABLE) {
        this.context=view.getContext();
        this.view=view;
        this.TABLE=TABLE;
        this.networkStatus=new NetworkStatus(context);
        tickets=view.findViewById(R.id.reservationEvent);
        reservationStatus=view.findViewById(R.id.reservationStatus);
    }

    /** Spočita kolko osob je nahlasených na udalosť*/
    @Override
    protected Void doInBackground(Object... objects) {
        if(networkStatus.isConnected()) {
            publishProgress(true);
            switch (TABLE) {
                case TB_EVENTS:
                    Event event = (Event) objects[0];
                    Sum sumCount = new Sum(context, Sum.EVENT_SUM_RESERVATION, event.getId());
                    try {
                        vacancies = Integer.parseInt(event.getCapacity()) - sumCount.getOccupancy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }

            publishProgress(false);
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Boolean... values) {
        if(networkStatus.isConnected()) {
            ProgressBar progressBar = view.findViewById(R.id.progressBar);

            if (values[0]) {
                progressBar.setVisibility(View.VISIBLE);
                tickets.setAlpha(0.2f);
                reservationStatus.setText("Overujem dostupnosť");
                reservationStatus.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                new ViewSetAlpha(tickets, 0.2f, 1.0f, 500);

            }
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(networkStatus.isConnected()) {
            ImageView ticket = view.findViewById(R.id.reservationEvent);

            if (vacancies <= 0) {
                reservationStatus.setText("Žiadne voľné miesta");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                ticket.setImageDrawable(context.getResources().getDrawable(R.drawable.ticket_no_free));
                new ViewSetAlpha(tickets, 1.0f, 0.2f, 700);
                ticket.setClickable(false);
            } else if (vacancies > 40) {
                reservationStatus.setText("40+ voľných miest");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                ticket.setImageDrawable(context.getResources().getDrawable(R.drawable.ticket_free));
                ticket.setClickable(true);
            } else if (vacancies > 20) {
                reservationStatus.setText("20+ voľných miest");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                ticket.setImageDrawable(context.getResources().getDrawable(R.drawable.ticket_free));
                ticket.setClickable(true);
            } else if (vacancies > 10) {
                reservationStatus.setText("10+ voľných miest");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                ticket.setImageDrawable(context.getResources().getDrawable(R.drawable.ticket_free));
                ticket.setClickable(true);
            } else if (vacancies <= 10 && vacancies > 4) {
                reservationStatus.setText("Posledných " + vacancies + " miest");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                ticket.setImageDrawable(context.getResources().getDrawable(R.drawable.ticket_free));
                ticket.setClickable(true);
            } else if (vacancies < 5 && vacancies > 1) {
                reservationStatus.setText("Posledné " + vacancies + " miesta!!!");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                ticket.setImageDrawable(context.getResources().getDrawable(R.drawable.ticket_free));
                ticket.setClickable(true);
            } else if (vacancies == 1) {
                reservationStatus.setText("1 posledné voľné miesto!!!");
                reservationStatus.setVisibility(View.VISIBLE);
                reservationStatus.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                ticket.setImageDrawable(context.getResources().getDrawable(R.drawable.ticket_free));
                ticket.setClickable(true);
            }
        }
        super.onPostExecute(aVoid);
    }
}
