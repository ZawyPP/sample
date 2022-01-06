package sk.zawy.lahodnosti.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.objects.DailyMenu;
import sk.zawy.lahodnosti.popUp.PopUpReservation;

public class ViewHolderDailyMenuAdapter {

    public TextView date;
    public TextView soup;
    public TextView mainMeal;
    public ImageView imgTickets;
    private DailyMenu data;

    public DailyMenu getData() {
        return data;
    }

    public void setData(DailyMenu data) {
        this.data = data;
    }

    public ViewHolderDailyMenuAdapter(View base) {
        mainMeal=(TextView)base.findViewById(R.id.main_meal);
        date=(TextView)base.findViewById(R.id.date);
        soup=(TextView)base.findViewById(R.id.soup);
        imgTickets=(ImageView)base.findViewById(R.id.reservationDailyMenu);

        imgTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new PopUpReservation(base.getContext(),PopUpReservation.POPUP_DAILYMENU_RESERVATION,getData(),null);

            }
        });

    }
}


