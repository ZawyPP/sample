package sk.zawy.lahodnosti.popUp;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import java.util.Map;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.control.EncryptData;
import sk.zawy.lahodnosti.login.Profil;
import sk.zawy.lahodnosti.sqlite.DatabaseHelper;
import sk.zawy.lahodnosti.sqlite.Select;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_DATE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_KIND;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_NAME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_TIME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_ID;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_DAY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_MAIN_MEAL;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_MENU_SOUP;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_COUNT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_ID_EVENT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_INFO;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;

public class PopUpOrder {

    private Context context;
    private String TABLE;

    private LayoutInflater layoutInflater=null;
    private View promptView=null;

    public TextView name;
    public TextView date;
    public TextView kind;
    public TextView infoOrder;
    public TextView countOrder;
    public TextView orderName;
    public TextView noteOrder;

    public PopUpOrder(Context context, String TABLE, Map result) {
        this.context = context;
        this.TABLE =TABLE;

        layoutInflater=LayoutInflater.from(context);
        promptView=layoutInflater.inflate(R.layout.popup_order,null);

        name=(TextView)promptView.findViewById(R.id.eName);
        date=(TextView)promptView.findViewById(R.id.eDate);
        kind=(TextView)promptView.findViewById(R.id.eKind);
        infoOrder =(TextView)promptView.findViewById(R.id.infoOrder);
        countOrder =(TextView)promptView.findViewById(R.id.countOrder);
        orderName=(TextView)promptView.findViewById(R.id.orderName);
        noteOrder=(TextView)promptView.findViewById(R.id.noteTextOrder);

        switch (TABLE){
            case TB_MENU:
                menuOrder(result);
                break;
            case TB_EVENTS:
                eventOrder(result);
                break;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(promptView)
                .show();

    }

    private void menuOrder(Map result) {
        name.setTextSize(19f);
        countOrder.setText((String) result.get(COLUMN_PERSON_COUNT));

        String info=(String) result.get(COLUMN_PERSON_INFO);

        if(info!=null) {
            if (info.contains("►") && info.contains("◄")) {
                String infoWithoutDiet=info.replaceAll(info.substring(info.indexOf("►"), info.indexOf("◄") + 1), "");
                if(infoWithoutDiet.length()>0) {
                    noteOrder.setVisibility(View.VISIBLE);
                    infoOrder.setText(infoWithoutDiet);
                }
            } else {
                if(info.length()>0) {
                    noteOrder.setVisibility(View.VISIBLE);
                    infoOrder.setText((String) result.get(COLUMN_PERSON_INFO));
                }
            }
        }
        orderName.setText(EncryptData.finalData(EncryptData.DECRYPT_MODE,Profil.getInstance().getReservation_name()));

        kind.setVisibility(View.INVISIBLE);

        Select select = new Select(context);
        DatabaseHelper db=new DatabaseHelper(context);
        Cursor cursor = select.dailyMenu((String) result.get(COLUMN_PERSON_ID_EVENT));

        while (cursor.moveToNext()) {
            name.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_SOUP)))+
                    "\n"+ cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_MAIN_MEAL)));
            date.setText(String.format("%s ( %s )",
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_DAY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))));
        }

        select.close();
    }

    private void eventOrder(Map result) {
        countOrder.setText((String) result.get(COLUMN_PERSON_COUNT));
        infoOrder.setText((String) result.get(COLUMN_PERSON_INFO));
        orderName.setText(EncryptData.finalData(EncryptData.DECRYPT_MODE,Profil.getInstance().getReservation_name()));

        Select select = new Select(context);
        DatabaseHelper db=new DatabaseHelper(context);
        Cursor cursor = select.event((String) result.get(COLUMN_PERSON_ID_EVENT));

        while (cursor.moveToNext()) {
            name.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_NAME)));
            date.setText(String.format("%s (o %s)",
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_TIME))));
            kind.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_KIND)));
        }

        select.close();
    }

}
