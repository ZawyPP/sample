package sk.zawy.lahodnosti.asyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.zawy.lahodnosti.BuildConfig;
import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.control.EncryptData;
import sk.zawy.lahodnosti.firebase.FirebaseDB;
import sk.zawy.lahodnosti.holder.ViewHolderOnlineReservation;
import sk.zawy.lahodnosti.holder.ViewHolderOrderDailyMenuOptions;
import sk.zawy.lahodnosti.login.Profil;
import sk.zawy.lahodnosti.network.NetworkStatus;
import sk.zawy.lahodnosti.objects.DailyMenu;
import sk.zawy.lahodnosti.objects.Event;
import sk.zawy.lahodnosti.popUp.PopUpOrder;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_DATE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_COST;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_COUNT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_FILLCOUNT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_ID_EVENT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_INFO;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_NAME;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_PHONE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_RESERVATION_KIND;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VISIBILITY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_PERSONS;

public class TaskCheckOccupancyAndMakeOrder extends AsyncTask<Object,Boolean,String> {
    public static final String CANCEL="cancel";
    public static final String CANCEL_SPECIAL_OPTIONS="cancel_special_options";
    public static final String SPECIAL_OPTION="special_options";
    private Context context;
    private String TABLE;
    private String WHAT;
    private String eventDate; //for easy way how orderBy orders
    private View view;
    private ImageView tickets;
    private int vacancies;
    private NetworkStatus networkStatus;
    private int actualOrderCount;
    private FirebaseDB db;
    private ViewHolderOnlineReservation viewHolder;
    private ViewHolderOrderDailyMenuOptions viewHolder2;
    private AlertDialog alertDialog;

    public TaskCheckOccupancyAndMakeOrder(View view, String TABLE, String actualOrderCount, String WHAT) {
        this.context=view.getContext();
        this.view=view;
        this.TABLE=TABLE;
        this.WHAT=WHAT;

        if(WHAT==null){
            this.viewHolder=new ViewHolderOnlineReservation(view);
        }else if(WHAT==SPECIAL_OPTION){
            this.viewHolder2=new ViewHolderOrderDailyMenuOptions(view);
        }else if(WHAT==CANCEL_SPECIAL_OPTIONS) {
            this.viewHolder2 = new ViewHolderOrderDailyMenuOptions(view);
        }else{
            this.viewHolder=new ViewHolderOnlineReservation(view);
        }

        this.networkStatus=new NetworkStatus(context);

        try {
            this.actualOrderCount = Integer.parseInt(actualOrderCount);
        }catch (Exception e){
            this.actualOrderCount = 0;
        }

    }

    /** Spočíta koľko osôb je nahlásených na udalosť*/
    @Override
    protected String doInBackground(Object... objects) {
        if(networkStatus.isConnected()) {
            publishProgress(true);

            String id="";
            db = new FirebaseDB(context);
            int capacity=0;
            switch (TABLE) {
                case TB_EVENTS:
                    Event event = (Event) objects[0];
                    /* Zistí aktualnú obsadenosť danej udalosti*/
                    id=event.getId();
                    db.setData(FirebaseDB.COUNT, id, null);
                    capacity=Integer.parseInt(event.getCapacity());
                    eventDate=event.getDate();
                    break;
                case TB_MENU:
                    DailyMenu dailyMenu = (DailyMenu) objects[0];
                    /* Zistí aktualnú obsadenosť danej udalosti*/
                    id=dailyMenu.getId();
                    db.setData(FirebaseDB.COUNT, id, null);
                    capacity=dailyMenu.getCapacity();
                    eventDate=dailyMenu.getId();
                    break;
            }

            Profil profil = Profil.getInstance();

            if(WHAT!=null){
                if(WHAT==CANCEL || WHAT==CANCEL_SPECIAL_OPTIONS) {
                    Map<String, Object> order = new HashMap<>();
                    order.put(COLUMN_PERSON_ID_EVENT, id);
                    order.put(COLUMN_PERSON_COUNT, "0");
                    order.put(COLUMN_RESERVATION_KIND, profil.getEmail());
                    order.put(COLUMN_VISIBILITY, 0);
                    order.put(COLUMN_EVENT_DATE, eventDate);

                    switch (WHAT){
                        case CANCEL:
                            order.put(COLUMN_PERSON_INFO, viewHolder.info.getText().toString());
                            break;
                        case CANCEL_SPECIAL_OPTIONS:
                            order.put(COLUMN_PERSON_INFO, viewHolder2.addInfo.getText().toString());
                            break;
                    }

                    db.setData(FirebaseDB.UPDATE, order, TB_PERSONS);
                    reportChange();//report edit order
                    publishProgress(false);
                    return null;
                }
            }


            //vacancies
            try {
                vacancies = capacity - db.getCount();
            } catch (Exception e) {
                e.printStackTrace();
            }
            vacancies += actualOrderCount; //vacancies and order count(from exist order)


            if(WHAT!=null){
                if(WHAT==SPECIAL_OPTION) {
                    if(viewHolder2!=null){

                        int countOrder= TextEdit.textToNumber(viewHolder2.count.getText().toString());
                        if(countOrder!=0) {
                            if(countOrder>8){
                                publishProgress(false);
                                return "OBJEDNAVKA ZAMIETNUTÁ \n Dôvod: Veľké objednávky je možné riešiť len telefonicky";
                            }
                            if (vacancies < countOrder) {
                                //ZAMIETNI OBJEDNAVKU
                                publishProgress(false);
                                return "OBJEDNAVKA ZAMIETNUTÁ \n Dôvod: nedostatok voľných miest";

                            } else {
                                //VYKONAJ OBJEDNAVKU
                                Map<String, Object> order = new HashMap<>();
                                order.put(COLUMN_PERSON_ID_EVENT, id);
                                if(profil.getDiscountDM().length()!=0 && !profil.getDiscountDM().equals("0")) {
                                    order.put(COLUMN_PERSON_NAME, profil.getReservation_name(" ►Zľava " + profil.getDiscountDM() + "%◄"));
                                }else{
                                    order.put(COLUMN_PERSON_NAME, profil.getReservation_name());
                                }
                                order.put(COLUMN_PERSON_PHONE, profil.getPhone());
                                order.put(COLUMN_PERSON_COUNT, String.valueOf(countOrder));
                                order.put(COLUMN_PERSON_INFO, viewHolder2.getOptions());
                                order.put(COLUMN_RESERVATION_KIND, profil.getEmail());
                                order.put(COLUMN_VISIBILITY, 1);
                                order.put(COLUMN_PERSON_FILLCOUNT, "0");
                                order.put(COLUMN_PERSON_COST, "0");
                                order.put(COLUMN_EVENT_DATE, eventDate);
                                order.put("VERSION", BuildConfig.VERSION_NAME);

                                db.setData(FirebaseDB.WRITE, order, TB_PERSONS);
                                reportChange();//report new order
                                publishProgress(false);

                                alertDialog.dismiss();
                                return String.valueOf((String)order.get(COLUMN_PERSON_ID_EVENT)+(String)order.get(COLUMN_RESERVATION_KIND));
                            }

                        }else if(countOrder==0){
                            return "Je nutné zadať počet porcií!";
                        }
                    }
                    return null;
                }
            }


            int countOrder=viewHolder.seekBar.getProgress();

                    if(countOrder!=0) {
                        if (vacancies < countOrder) {
                            //ZAMIETNI OBJEDNAVKU
                            publishProgress(false);
                            return "OBJEDNAVKA ZAMIETNUTÁ \n Dôvod: nedostatok voľných miest";

                        } else {
                            //VYKONAJ OBJEDNAVKU
                            Map<String, Object> order = new HashMap<>();
                            order.put(COLUMN_PERSON_ID_EVENT, id);
                            if(TABLE==TB_MENU){
                                if(profil.getDiscountDM().length()!=0  && !profil.getDiscountDM().equals("0")) {
                                    order.put(COLUMN_PERSON_NAME, profil.getReservation_name(" ►Zľava " + profil.getDiscountDM() + "%◄"));
                                }else{
                                    order.put(COLUMN_PERSON_NAME, profil.getReservation_name());
                                }
                            }else{
                                order.put(COLUMN_PERSON_NAME, profil.getReservation_name());
                            }
                            order.put(COLUMN_PERSON_PHONE, profil.getPhone());
                            order.put(COLUMN_PERSON_COUNT, String.valueOf(countOrder));
                            order.put(COLUMN_PERSON_INFO, viewHolder.info.getText().toString());
                            order.put(COLUMN_RESERVATION_KIND, profil.getEmail());
                            order.put(COLUMN_VISIBILITY, 1);
                            order.put(COLUMN_PERSON_FILLCOUNT, "0");
                            order.put(COLUMN_PERSON_COST, "0");
                            order.put(COLUMN_EVENT_DATE, eventDate);
                            order.put("VERSION",BuildConfig.VERSION_NAME);

                            if(actualOrderCount==0) {
                                db.setData(FirebaseDB.WRITE, order, TB_PERSONS);
                            }else{
                                db.setData(FirebaseDB.UPDATE, order, TB_PERSONS);
                            }
                            reportChange();//report new order
                            publishProgress(false);
                            return String.valueOf((String)order.get(COLUMN_PERSON_ID_EVENT)+(String)order.get(COLUMN_RESERVATION_KIND));
                        }
                    }else if(countOrder==0){

                    }

            }
            publishProgress(false);

        return "POŽIADAVKU NIEJE MOŽNÉ VYKONAŤ, SKONTROLUJTE SIEŤ (RESP. POUŽITE MOŽNOSŤ TELEFONICKEJ REZERVÁCIE)";
    }


    @Override
    protected void onProgressUpdate(Boolean... values) {

        if (values[0]){
            //todo progress
        }else{

        }

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String text) {
        AlertDialog alertDialog;

        if(text==null) {
            alertDialog = new AlertDialog.Builder(context).setMessage("REZERVÁCIA ZRUŠENÁ").setNegativeButton(R.string.close,null).show();
        }else if(text.contains(" ")) {
            alertDialog = new AlertDialog.Builder(context).setMessage(text).setNegativeButton(R.string.close,null).show();
        }else {
            List<String> id = new ArrayList<>();
            id.add(text);
            db.setData(FirebaseDB.READ_ALL, id, TB_PERSONS);

            if (db.getResult().size() == 0) {
                //alertDialog NEUSPESNE
                alertDialog = new AlertDialog.Builder(context).setMessage("REZERVÁCIA NEÚSPEŠNÁ").setNegativeButton(R.string.close, null).show();
            } else {
                //alertDialog Uspesne
                new PopUpOrder(context,TABLE,db.getResult());
            }

        }
    }

    public void setPopUp(AlertDialog alertDialog) {
        this.alertDialog=alertDialog;
    }

    public void reportChange(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("updateData")
                .document("DMEPERS")
                .update(FirebaseAuth.getInstance().getCurrentUser().getUid(),System.currentTimeMillis());
    }

}
