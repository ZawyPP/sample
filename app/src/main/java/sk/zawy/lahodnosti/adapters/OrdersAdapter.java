package sk.zawy.lahodnosti.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.DateCompare;
import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.holder.ViewHolderOrderListRow;
import sk.zawy.lahodnosti.network.NetworkStatus;
import sk.zawy.lahodnosti.objects.DailyMenu;
import sk.zawy.lahodnosti.objects.Event;
import sk.zawy.lahodnosti.popUp.PopUpReservation;
import sk.zawy.lahodnosti.sqlite.PopulateFromSQLite;
import sk.zawy.lahodnosti.sqlite.StaticValue;
import sk.zawy.lahodnosti.view.Blick;
import sk.zawy.lahodnosti.view.DrawableRadius;

public class OrdersAdapter extends ArrayAdapter<Object> implements AdapterView.OnItemClickListener{
    private Context context;
    private List dataOrder;
    private int resource;
    private ViewHolderOrderListRow holder;
    private Map dailyMenuMap=new HashMap<>();
    private Map eventMap=new HashMap<>();
    private AlertDialog alertDialog;

    public OrdersAdapter(@NonNull Context context, int resource, @NonNull List<Object> data, AlertDialog.Builder alertdialog)  {
        super(context, resource, data);

        this.context=context;
        this.dataOrder =data;
        this.resource=resource;
        alertDialog=alertdialog.create();

       this.alertDialog.show();

        populateDataFromSQLite(data);

    }

    /** get data for event/dailymenu order */
    private void populateDataFromSQLite(List data) {
        Set<String> idsEvents=new HashSet<>();
        Set<String> idsDailymenu=new HashSet<>();

        for(Object object : data) {
            String id= ((Map)object).get(StaticValue.COLUMN_PERSON_ID_EVENT).toString();

            if(id.contains("-")){
                idsDailymenu.add(id);
            }else{
                idsEvents.add(id);
            }
            id=null;
        }

        if(idsEvents.size()>0) {
            PopulateFromSQLite populateEvent = new PopulateFromSQLite(context, PopulateFromSQLite.POPULATE_EVENTS_WHERE_ID, stringBuilderID(idsEvents));
            eventMap = populateEvent.getDataMap();
        }
        if(idsDailymenu.size()>0) {
            PopulateFromSQLite populateDailymenu = new PopulateFromSQLite(context, PopulateFromSQLite.POPULATE_DAILY_MENU_WHERE_ID, stringBuilderID(idsDailymenu));
            dailyMenuMap = populateDailymenu.getDataMap();
        }
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(resource,parent,false);

        holder=(ViewHolderOrderListRow)row.getTag();

        if(holder==null){
            holder=new ViewHolderOrderListRow(row);
            row.setTag(holder);
        }else{
            row.getTag();
        }

        Map map=(Map)dataOrder.get(position);
        if(((String)map.get(StaticValue.COLUMN_PERSON_ID_EVENT)).contains("-")){
            DailyMenu dailyMenu=(DailyMenu)dailyMenuMap.get(map.get(StaticValue.COLUMN_PERSON_ID_EVENT));

            holder.name.setTextSize(15f);
            holder.name.setSingleLine(false);
            holder.time.setVisibility(View.GONE);
            if(dailyMenu!=null) {
                try {
                    holder.name.setText(dailyMenu.getSoup().substring(0, 40) + "... \n" + dailyMenu.getMailMeal().substring(0, 40) + "...");
                } catch (Exception e) {
                    holder.name.setText(dailyMenu.getSoup() + "\n" + dailyMenu.getMailMeal());
                }
                holder.price.setText(dailyMenu.getPrice() + " €/ks");
                holder.date.setText(TextEdit.localDateFormat(dailyMenu.getId(), TextEdit.DAY_MONTH_DAYNAME) );
            }else{
                holder.name.setText("???");
            }
            holder.count.setText((String)map.get(StaticValue.COLUMN_PERSON_COUNT) + " x");
            holder.kind.setText("OBEDOVÉ MENU");

            holder.timeText.setVisibility(View.GONE);
            setKindColor(holder,"OBEDOVÉ MENU");

            // check actuality for edit
            if(dailyMenu!=null) {
                checkEventAndActualDate(holder, dailyMenu.getId(), 10, 30, map);
            }else{
                checkEventAndActualDate(holder,"2020-01-01",0,0,map);
            }

        }else{
            Event event=(Event)eventMap.get(map.get(StaticValue.COLUMN_PERSON_ID_EVENT));

            holder.count.setText((String) map.get(StaticValue.COLUMN_PERSON_COUNT) + " x");

            if(event!=null) {
                holder.name.setText(event.getName());

                holder.price.setText(event.getPrice() + " €/ks");
                holder.kind.setText(event.getKind());
                holder.date.setText(TextEdit.localDateFormat(event.getDate(), TextEdit.DAY_MONTH_DAYNAME));
                holder.time.setText(event.getTime());
                if (event.isEnable() == 0) {
                    holder.disable.setVisibility(View.VISIBLE);
                }
                setKindColor(holder,event.getKind());
            }else{
                holder.kind.setBackgroundResource(R.drawable.background_kind_green);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.kind.setText("???????");
            }


            // check actuality for edit
            if(event!=null) {
                checkEventAndActualDate(holder,
                        event.getDate(),
                        Integer.parseInt(event.getTime().substring(0, event.getTime().indexOf(":"))),
                        Integer.parseInt(event.getTime().substring(event.getTime().indexOf(":") + 1, event.getTime().indexOf(":") + 3)),
                        map);
            }else{
                checkEventAndActualDate(holder,
                        "2020-01-01",0,0, map);
            }
        }

        return row;
    }

    private void setKindColor(ViewHolderOrderListRow holder, String kind) {
        switch (kind) {
            case "KONCERT":
                holder.kind.setBackgroundResource(R.drawable.background_kind_green);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
            case "LITERATÚRA":
                holder.kind.setBackgroundResource(R.drawable.background_kind_yellow);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.black));
                break;
            case "KINO":
                holder.kind.setBackgroundResource(R.drawable.backgroung_kind_black);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
            case "DISKUSIA":
                holder.kind.setBackgroundResource(R.drawable.background_kind_red);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
            case "WORKSHOP":
                holder.kind.setBackgroundResource(R.drawable.background_kind_brown);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
            case "OBEDOVÉ MENU":
                holder.kind.setBackgroundResource(R.drawable.background_kind_dm);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.black));
                break;
        }
    }

    private void checkEventAndActualDate(ViewHolderOrderListRow holder, String date, int hours, int minutes, Map map){
        if(new DateCompare(hours,minutes).isActual(date)) {
            //actual
            if(new DateCompare(0,0).isToday(date)){
                //today
                new Blick().startBlick(context,holder.todayLay);
                new DrawableRadius(context, holder.rowLay, 35f).setBackgroundAndRadius(R.color.orderToday, 5f, 30f, 5f, 30f);
            }else {
                new DrawableRadius(context, holder.rowLay, 35f).setBackgroundAndRadius(android.R.color.white, 5f, 30f, 5f, 30f);
            }
        }else{
            //neaktualne
            if(new DateCompare(23,59).isActual(date)) {
                //today event date but after start time
                new DrawableRadius(context,holder.rowLay,35f).setBackgroundAndRadius(R.color.version,5f,5f,5f,5f);
            }else {
                //next day(after event date)
                if (map.get(StaticValue.COLUMN_PERSON_FILLCOUNT) == null) {
                    //Erroy input data
                    new DrawableRadius(context,holder.rowLay,35f).setBackgroundAndRadius(R.color.orderWrong,30f,5f,30f,5f);
                } else {

                    if (((String) map.get(StaticValue.COLUMN_PERSON_COUNT)).equals((String) map.get(StaticValue.COLUMN_PERSON_FILLCOUNT))) {
                        //they came all - OK status
                        holder.rowLay.setAlpha(0.55f);
                        new DrawableRadius(context,holder.rowLay,35f).setBackgroundAndRadius(R.color.orderOk,30f,5f,30f,5f);
                    } else if (((String) map.get(StaticValue.COLUMN_PERSON_FILLCOUNT)).equals("0")) {
                        //person's count is 0 - WRONG status
                        holder.rowLay.setAlpha(0.55f);
                        new DrawableRadius(context,holder.rowLay,35f).setBackgroundAndRadius(R.color.orderWrong,30f,5f,30f,5f);
                    } else {
                        //person's count isn't 0 but did not come all
                        holder.rowLay.setAlpha(0.55f);
                        new DrawableRadius(context,holder.rowLay,35f).setBackgroundAndRadius(R.color.unactiveOnlineReservation,30f,5f,30f,5f);
                    }
                }

            }
        }
    }



    private String stringBuilderID(Set<String> idList) {
        StringBuilder allID=new StringBuilder();

        for(String id : idList){
            if(allID.length()>0) {
                allID.append(" , \"" + id + "\"");
            }else {
                allID.append("\"" + id + "\"");
            }
        }
        return String.valueOf(allID);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (new NetworkStatus(context).isConnected()) {

            Map map = (Map) dataOrder.get(position);

            if (((String) map.get(StaticValue.COLUMN_PERSON_FILLCOUNT)).equals("0")) { //este sa nevpúšťa do miestnosti

                if (((String) map.get(StaticValue.COLUMN_PERSON_ID_EVENT)).contains("-")) {
                    //daily
                    //compare actual time
                    if (new DateCompare(10, 30).isActual((map.get(StaticValue.COLUMN_EVENT_DATE).toString()))) {
                        DailyMenu dailyMenu = (DailyMenu) dailyMenuMap.get(map.get(StaticValue.COLUMN_PERSON_ID_EVENT));
                        new PopUpReservation(context, PopUpReservation.POPUP_DAILYMENU_RESERVATION, dailyMenu,1);
                    }else{
                        return;
                    }
                } else {
                    //event
                    Event event = (Event) eventMap.get(map.get(StaticValue.COLUMN_PERSON_ID_EVENT));

                    if(event.isEnable()==0){
                        Toast.makeText(context,"Udalosť zrušená!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    //compare actual time and event's time
                    if (new DateCompare((Integer.parseInt(event.getTime().substring(0, event.getTime().indexOf(":"))))-1,
                            Integer.parseInt(event.getTime().substring(event.getTime().indexOf(":") + 1, event.getTime().indexOf(":") + 3))).isActual(event.getDate())) {
                        new PopUpReservation(context, PopUpReservation.POPUP_EVENT_RESERVATION, event,1);
                    }else{
                        return;
                    }
                }

                alertDialog.cancel();
            } else{
                Toast.makeText(context, context.getResources().getString(R.string.no_order_edit),Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(context, context.getResources().getString(R.string.no_connect_for_edit_order), Toast.LENGTH_LONG).show();
        }
    }
}
