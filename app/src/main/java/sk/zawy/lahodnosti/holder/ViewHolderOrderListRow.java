package sk.zawy.lahodnosti.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;

public class ViewHolderOrderListRow {
    public TextView date;
    public TextView kind;
    public TextView timeText;
    public TextView time;
    public TextView name;
    public TextView price;
    public TextView count;
    public TextView disable;

    public RelativeLayout rowLay;
    public LinearLayout todayLay;

    public ViewHolderOrderListRow(View base) {
        disable=(TextView)base.findViewById(R.id.orderDisable);
        date=(TextView)base.findViewById(R.id.orderDate);
        kind=(TextView)base.findViewById(R.id.orderKind);
        timeText=(TextView)base.findViewById(R.id.orderTime);
        time=(TextView)base.findViewById(R.id.orderTimeStart);
        name=(TextView)base.findViewById(R.id.oName);
        price=(TextView)base.findViewById(R.id.oPrice1);
        count=(TextView)base.findViewById(R.id.oCount);
        rowLay=(RelativeLayout) base.findViewById(R.id.orderRow);
        todayLay=(LinearLayout) base.findViewById(R.id.todayEvent);

    }


}
