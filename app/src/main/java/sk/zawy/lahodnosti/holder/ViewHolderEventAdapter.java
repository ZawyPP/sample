package sk.zawy.lahodnosti.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.objects.Event;
import sk.zawy.lahodnosti.popUp.PopUpDetail;
import sk.zawy.lahodnosti.popUp.PopUpReservation;

public class ViewHolderEventAdapter implements View.OnClickListener {
    private View base;
    public TextView disable;
    public TextView name;
    public TextView date;
    public TextView kind;
    public TextView price1;
    public TextView price2;
    public TextView priceTextView;
    public TextView priceTextView1;
    public ImageView imageView;
    public ImageView imgTickets;
    public LinearLayout item;
    private Event data;

    public Event getData() {
        return data;
    }

    public void setData(Event data) {
        this.data = data;
    }

    public ViewHolderEventAdapter(View base) {
        this.base=base;
        disable=(TextView)base.findViewById(R.id.eDisable);
        name=(TextView)base.findViewById(R.id.eName);
        date=(TextView)base.findViewById(R.id.eDate);
        kind=(TextView)base.findViewById(R.id.eKind);
        price1=(TextView)base.findViewById(R.id.ePrice1);
        price2=(TextView)base.findViewById(R.id.ePrice2);
        priceTextView=(TextView)base.findViewById(R.id.ePriceTextView);
        priceTextView1=(TextView)base.findViewById(R.id.ePriceTextView1);
        imageView=(ImageView)base.findViewById(R.id.imageView);
        imgTickets=(ImageView)base.findViewById(R.id.reservationEvent);
        item=(LinearLayout) base.findViewById(R.id.eLinearLay);

        imgTickets.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reservationEvent:
                new PopUpReservation(base.getContext(),PopUpReservation.POPUP_EVENT_RESERVATION,getData(),null);
                break;
            case R.id.imageView:
                new PopUpDetail(base.getContext(), PopUpDetail.POPUP_EVENT_PHOTO_ZOOM,getData());
                break;
        }

    }

}
