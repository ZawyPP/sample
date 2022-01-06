package sk.zawy.lahodnosti.popUp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import sk.zawy.lahodnosti.R;

public class PopUpFirstStart {

    public PopUpFirstStart(Context context) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View promptView=layoutInflater.inflate(R.layout.popup_first,null);

        AlertDialog alertDialog=new AlertDialog.Builder(context).setView(promptView).setNegativeButton("Zavrieť", null).show();
    }
}
