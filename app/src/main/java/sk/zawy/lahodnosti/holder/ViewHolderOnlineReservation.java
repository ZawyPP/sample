package sk.zawy.lahodnosti.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import org.w3c.dom.Text;

import sk.zawy.lahodnosti.R;

public class ViewHolderOnlineReservation {

        public TextView name;
        public TextView phone;
        public TextView count;
        public TextView info;
        public TextView textInfoSeekBar;
        public TextView actualOrderCount;
        public SeekBar seekBar;
        public ImageButton okButton;
        public ImageButton cancelButton;
        public LinearLayout layoutOrderID;
        public LinearLayout textForButton;
        public CheckBox special;

        public ViewHolderOnlineReservation(View base) {
            name=(TextView)base.findViewById(R.id.user_name);
            phone=(TextView)base.findViewById(R.id.user_phone);
            count=(TextView)base.findViewById(R.id.many_seats);
            info=(TextView)base.findViewById(R.id.infoToOrder);
            textInfoSeekBar=(TextView)base.findViewById(R.id.reservationLunch);
            actualOrderCount =(TextView)base.findViewById(R.id.user_order_id);
            seekBar=(SeekBar) base.findViewById(R.id.seekBar);
            okButton=(ImageButton) base.findViewById(R.id.placeAnOrder);
            cancelButton=(ImageButton) base.findViewById(R.id.cancelOrder);
            textForButton=(LinearLayout)base.findViewById(R.id.layButtonText);
            layoutOrderID=(LinearLayout) base.findViewById(R.id.layout_order_id);
            special=(CheckBox)base.findViewById(R.id.checkBoxSpecial);

        }

}
