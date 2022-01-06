package sk.zawy.lahodnosti.popUp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.Call;
import sk.zawy.lahodnosti.accessories.DateCompare;
import sk.zawy.lahodnosti.asyncTasks.TaskCheckOccupancyAndMakeOrder;
import sk.zawy.lahodnosti.asyncTasks.TaskCheckOrderExist;
import sk.zawy.lahodnosti.asyncTasks.TaskRealtimeOccupancy;
import sk.zawy.lahodnosti.control.EncryptData;
import sk.zawy.lahodnosti.holder.ViewHolderOnlineReservation;
import sk.zawy.lahodnosti.holder.ViewHolderOrderDailyMenuOptions;
import sk.zawy.lahodnosti.login.Profil;
import sk.zawy.lahodnosti.objects.DailyMenu;
import sk.zawy.lahodnosti.objects.Event;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_PERSONS;

public class PopUpReservation implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final int POPUP_DAILYMENU_RESERVATION=0;
    public static final int POPUP_EVENT_RESERVATION=1;

    private Context context=null;
    private LayoutInflater layoutInflater=null;
    private View promptView=null;
    private View promptView2=null;

    private AlertDialog alertDialog;
    private Object object;
    private DailyMenu dailyMenu;
    private String table;
    private boolean onlineReservation=true;
    private ViewFlipper viewFlipperForType;
    private ImageView imageButton;
    private ImageView imageButton2;
    private ImageButton orderButton;
    private TextView userNameText;
    private TextView userPhoneText;
    private EditText detailToOrder;
    private String id="";
    private ViewHolderOnlineReservation viewHolder;
    private ViewHolderOrderDailyMenuOptions holder2;

    public PopUpReservation(Context context,int WHICH_ONE,Object object,Integer flipperPosition) {
        this.context = context;
        this.object = object;

        layoutInflater=LayoutInflater.from(context);
        promptView=layoutInflater.inflate(R.layout.popup_reservation,null);
        promptView2=layoutInflater.inflate(R.layout.popup_reservation_options,null);
        viewHolder=new ViewHolderOnlineReservation(promptView);

        switch (WHICH_ONE){
            case POPUP_DAILYMENU_RESERVATION:
                viewHolder.info.setVisibility(View.GONE);
                viewHolder.special.setVisibility(View.VISIBLE);
                viewHolder.textInfoSeekBar.setText(R.string.textCountLunch);
                table=TB_MENU;
                dailyMenu=(DailyMenu)object;
                new TaskRealtimeOccupancy(promptView, TB_MENU).execute(dailyMenu);
                id=dailyMenu.getId();
                break;
            case POPUP_EVENT_RESERVATION:
                viewHolder.info.setVisibility(View.VISIBLE);
                viewHolder.special.setVisibility(View.GONE);
                table=TB_EVENTS;
                Event event=(Event)object;
                onlineReservation=event.isOnlineReservation()==1;
                new TaskRealtimeOccupancy(promptView, TB_EVENTS).execute(event);
                id=event.getId();
                break;
        }

        startAlertDialog(flipperPosition);

    }

    private void startAlertDialog(Integer flipperPosition) {
        viewFlipperForType = (ViewFlipper)promptView.findViewById(R.id.flipperForType);
        viewHolder.special.setOnCheckedChangeListener(this);

        imageButton=(ImageView)promptView.findViewById(R.id.phone);
        imageButton2=(ImageView)promptView.findViewById(R.id.network);

        imageButton.setClickable(true);
        imageButton.setOnClickListener(this);

        if(flipperPosition!=null){
            viewFlipperForType.setDisplayedChild(flipperPosition); //edit order-select network
            imageButton.setBackground(context.getResources().getDrawable(R.drawable.button_unactive_selector));
            imageButton2.setBackground(context.getResources().getDrawable(R.color.colorAccent));
            new TaskCheckOrderExist(promptView,promptView2,TB_PERSONS).execute(id);
        }
        //If not allowed online reservation or User hasn't permissions, this option will be hidden
        if(onlineReservation && Profil.getInstance().isOnlineReservation()) {
            viewHolder.name.setText(EncryptData.finalData(EncryptData.DECRYPT_MODE,Profil.getInstance().getReservation_name()));
            viewHolder.phone.setText(EncryptData.finalData(EncryptData.DECRYPT_MODE,Profil.getInstance().getPhone()));
            imageButton2.setClickable(true);
            viewHolder.okButton.setClickable(true);
            imageButton2.setOnClickListener(this);
            viewHolder.okButton.setOnClickListener(this);
            viewHolder.cancelButton.setOnClickListener(this);

            viewHolder.info.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    //when click on insert detail, table will bigger
                    viewHolder.info.setLines(5);
                }
            });

        }else{
            imageButton2.setVisibility(View.INVISIBLE);
        }

        switch (table) {
            case TB_MENU:
                try {
                    DateCompare dateCompare = new DateCompare(10, 30);
                    if (dateCompare.isActual(dailyMenu.getId())) {
                        imageButton2.setVisibility(View.VISIBLE);
                    } else {
                        imageButton2.setVisibility(View.INVISIBLE);
                        imageButton2.setClickable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }




        ImageButton phoneButton=(ImageButton)promptView.findViewById(R.id.phoneButton);
        phoneButton.setOnClickListener(this);

        alertDialog=new AlertDialog.Builder(context)
                .setView(promptView).show();

        viewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                viewHolder.count.setText(String.valueOf(i));
                viewHolder.count.setTextColor(context.getResources().getColor(android.R.color.white));
                if(i==0){
                    viewHolder.okButton.setEnabled(false);
                    viewHolder.okButton.setAlpha(0.3f);
                }else{
                    viewHolder.okButton.setEnabled(true);
                    viewHolder.okButton.setAlpha(1.0f);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.network:
                if(onlineReservation) {
                    viewFlipperForType.setDisplayedChild(1);
                    imageButton.setBackground(context.getResources().getDrawable(R.drawable.button_unactive_selector));
                    imageButton2.setBackground(context.getResources().getDrawable(R.color.colorAccent));
                    new TaskCheckOrderExist(promptView,promptView2,TB_PERSONS).execute(id);
                }
                break;
            case R.id.phone:
                viewFlipperForType.setDisplayedChild(0);
                imageButton2.setBackground(context.getResources().getDrawable(R.drawable.button_unactive_selector));
                imageButton.setBackground(context.getResources().getDrawable(R.color.colorAccent));
                break;
            case R.id.phoneButton:
                new Call(context,context.getResources().getString(R.string.call_phone_number));
                break;
            case R.id.placeAnOrder:
                if(viewHolder.seekBar.getProgress()!=0){
                    switch (table){
                        case TB_MENU:
                            new TaskCheckOccupancyAndMakeOrder(promptView, table,viewHolder.actualOrderCount.getText().toString(),null).execute((DailyMenu) object);
                            break;
                        case TB_EVENTS:
                            new TaskCheckOccupancyAndMakeOrder(promptView,table,viewHolder.actualOrderCount.getText().toString(),null).execute((Event)object);
                            break;
                    }
                    alertDialog.cancel();
                }else{
                    viewHolder.count.setText("???!");
                    viewHolder.count.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                    viewHolder.okButton.setEnabled(false);
                    viewHolder.okButton.setAlpha(0.3f);
                }
                break;
            case R.id.cancelOrder:
                AlertDialog.Builder alertDialog2=new AlertDialog.Builder(context);
                alertDialog2
                        .setMessage(context.getResources().getString(R.string.delete_reservation_q))
                        .setNegativeButton("NIE", null);

                switch (table){
                    case TB_MENU:
                        alertDialog2
                                .setPositiveButton(context.getResources().getString(R.string.yes_delete), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new TaskCheckOccupancyAndMakeOrder(promptView, table,viewHolder.actualOrderCount.getText().toString(),TaskCheckOccupancyAndMakeOrder.CANCEL).execute((DailyMenu) object);
                                        alertDialog.cancel();
                                    }
                                }).show();

                        break;
                    case TB_EVENTS:
                        alertDialog2
                                .setPositiveButton(context.getResources().getString(R.string.yes_delete), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new TaskCheckOccupancyAndMakeOrder(promptView,table,viewHolder.actualOrderCount.getText().toString(),TaskCheckOccupancyAndMakeOrder.CANCEL).execute((Event)object);
                                        alertDialog.cancel();
                                    }
                                }).show();
                        break;
                }

                break;
            case R.id.placeAnOrder2:
                if(holder2.getOptions()!=null){
                    switch (table) {
                        case TB_MENU:
                            TaskCheckOccupancyAndMakeOrder task=new TaskCheckOccupancyAndMakeOrder(promptView2, table, holder2.count.getText().toString(), TaskCheckOccupancyAndMakeOrder.SPECIAL_OPTION);
                            task.setPopUp(alertDialog);
                            task.execute((DailyMenu) object);
                            break;
                    }
                }
                break;
            case R.id.cancelOrder2:
                AlertDialog.Builder alertDialog3=new AlertDialog.Builder(context);
                alertDialog3
                        .setMessage(context.getResources().getString(R.string.delete_reservation_q))
                        .setNegativeButton("NIE", null)
                        .setPositiveButton(context.getResources().getString(R.string.yes_delete), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new TaskCheckOccupancyAndMakeOrder(promptView2, table,holder2.count.getText().toString(),TaskCheckOccupancyAndMakeOrder.CANCEL_SPECIAL_OPTIONS).execute((DailyMenu) object);
                                alertDialog.cancel();
                            }
                        }).show();
                ;
                break;

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkBoxSpecial:
                if(isChecked){
                    alertDialog.dismiss();
                    if(viewHolder.seekBar.getProgress()!=0) {
                        startAlertDialog2(viewHolder.seekBar.getProgress());
                    }else{
                        startAlertDialog2(null);
                    }
                }
                break;
        }
    }

    private void startAlertDialog2(Integer seekBar){
        holder2=new ViewHolderOrderDailyMenuOptions(promptView2);
        holder2.okButtonSpecial.setOnClickListener(this);
        holder2.cancelButtonSpecial.setOnClickListener(this);

        if(dailyMenu.getNotification().contains("@")){
            holder2.deliveryLin.setVisibility(View.GONE);
        }   //zakazane dorucenie

        if(seekBar!=null){
            holder2.classicBox.setChecked(true);
            holder2.classicPortion.setText(seekBar.toString());
        }
        alertDialog=new AlertDialog.Builder(context)
                .setView(promptView2).show();


    }

}
