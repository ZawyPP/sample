package sk.zawy.lahodnosti.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;

public class ViewHolderBookAdapter {
    public ImageView logo;
    public TextView noLogo;

    public ViewHolderBookAdapter(Context context, View base) {
        View view = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        if(base!=null){
            logo=(ImageView)base.findViewById(R.id.row_logo);
            noLogo=(TextView) base.findViewById(R.id.row_book_text);
        }

    }
}
