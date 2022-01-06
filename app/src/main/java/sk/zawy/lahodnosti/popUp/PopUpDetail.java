package sk.zawy.lahodnosti.popUp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.DateCompare;
import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.asyncTasks.TaskSum;
import sk.zawy.lahodnosti.objects.Event;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;

public class PopUpDetail {
    public static final int POPUP_EVENT_PHOTO_ZOOM=0;
    public static final int POPUP_EVENT_INFO=1;
    private Context context;
    private Object object=null;

    public PopUpDetail(Context context,int WHICH_ONE,Object object) {
        this.context = context;
        this.object = object;

        switch (WHICH_ONE){
            case POPUP_EVENT_PHOTO_ZOOM:
                open_event_photo();
                break;
            case POPUP_EVENT_INFO:
                open_event_info();
                break;
        }
    }

    private void open_event_info() {
        Event event=(Event) object;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.popup_info, null);

        if(event.isEnable()!=0) {
            if (new DateCompare(22, 00).isActual(event.getDate())) {
                new TaskSum(promptView, TB_EVENTS).execute(event);
            }
        }
        AlertDialog alertDialog=new AlertDialog.Builder(context)
                .setView(promptView).show();

        TextView title=(TextView)promptView.findViewById(R.id.title);
        TextView dateTime=(TextView)promptView.findViewById(R.id.dateTime);
        TextView price1=(TextView)promptView.findViewById(R.id.ePrice1);
        TextView price2=(TextView)promptView.findViewById(R.id.ePrice2);
        TextView infoMain=(TextView)promptView.findViewById(R.id.infoMain);
        TextView kind=(TextView)promptView.findViewById(R.id.kind);
        RelativeLayout layout=(RelativeLayout)promptView.findViewById(R.id.layout_info);

        title.setText(event.getName());
        kind.setText(event.getKind());
        dateTime.setText(TextEdit.localDateFormat(event.getDate(),TextEdit.DAY_MONTH_DAYNAME)+
                " " + event.getTime() + " " + event.getTime2());
        if(event.getPrice().equals("0")){
            price1.setText(context.getResources().getText(R.string.voluntary_price));
            price1.setTypeface(price1.getTypeface(), Typeface.NORMAL);
            price2.setVisibility(View.INVISIBLE);
        }else if(!event.getPrice().equals("0") && !event.getPrice().equals("-1")){
            price1.setText(context.getResources().getText(R.string.price1) + " " + event.getPrice() + " €");
        }

        if(event.getPrice2().length()<1||
                event.getPrice2().equals("0")){
            price2.setVisibility(View.INVISIBLE);
        }else{
            price2.setText(context.getResources().getText(R.string.price2) + " " + event.getPrice2() + " €");
        }

        if(event.isEnable()!=0) {
            if (new DateCompare(Integer.parseInt(event.getTime().substring(0, event.getTime().indexOf(":"))),
                    Integer.parseInt(event.getTime().substring(event.getTime().indexOf(":") + 1, event.getTime().indexOf(":") + 3))).isActual(event.getDate())) {
                ImageView reservation = (ImageView) promptView.findViewById(R.id.reservationEvent);
                reservation.setVisibility(View.VISIBLE);
                reservation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                        new PopUpReservation(context, PopUpReservation.POPUP_EVENT_RESERVATION, event, null);
                    }
                });
            }
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        if(event.getUrl().length()>0) {
            ImageView photo = (ImageView) promptView.findViewById(R.id.zoomPhoto);
            Picasso.get().load(event.getUrl()).into(photo);
        }

        if(event.getInfoMain().length()>0) {
            infoMain.setText(event.getInfoMain());
        }
    }




    private void open_event_photo() {
        Event event=(Event) object;

        if(event.getUrl().length()>0) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View promptView = layoutInflater.inflate(R.layout.popup_photo, null);
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setView(promptView)
                    .show();

            ImageView photo = (ImageView) promptView.findViewById(R.id.zoomPhoto);
            ImageView closePict = (ImageView) promptView.findViewById(R.id.closePict);
            closePict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                }
            });
            Picasso.get().load(event.getUrl()).into(photo);
        }
    }
}
