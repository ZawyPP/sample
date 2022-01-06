package sk.zawy.lahodnosti.popUp;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.adapters.OrdersAdapter;
import sk.zawy.lahodnosti.holder.ViewHolderOnlineReservation;
import sk.zawy.lahodnosti.login.Profil;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_EVENT_DATE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_COUNT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VISIBILITY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_PERSONS;

public class PopUpOrdersList {
    private Context context;
    private LayoutInflater layoutInflater=null;
    private View promptView=null;
    private ListView listView;
    private AlertDialog.Builder alertdialog;

    public PopUpOrdersList(Context context) {
        this.context=context;

        layoutInflater=LayoutInflater.from(context);
        promptView=layoutInflater.inflate(R.layout.popup_order_list,null);

        this.listView=(ListView)promptView.findViewById(R.id.listViewOrders);

        getData(Profil.getInstance().getEmail());

        alertdialog=new AlertDialog.Builder(context);
        alertdialog.setView(promptView);
    }


    private void getData(String email){

        FirebaseFirestore dbFireStrore=FirebaseFirestore.getInstance();
        ArrayList data = new ArrayList<>();

        dbFireStrore.collection(TB_PERSONS)
                .whereEqualTo("kind",email)
                .orderBy(COLUMN_EVENT_DATE, Query.Direction.DESCENDING)
                .limit(40)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot>list=queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot: list){
                        if(documentSnapshot.getData().get(COLUMN_VISIBILITY).toString().equals("1")) {
                            data.add(documentSnapshot.getData());
                        }
                    }
                    OrdersAdapter adapter=new OrdersAdapter(context,R.layout.row_order,data,alertdialog);
                    listView.setAdapter(adapter);
                    listView.setClickable(true);
                    listView.setOnItemClickListener(adapter);

                }else {
                    Toast.makeText(context,"Žiadne rezervácie!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
