package sk.zawy.lahodnosti.firebase;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.asyncTasks.TaskSynchDB;
import sk.zawy.lahodnosti.login.Profil;
import sk.zawy.lahodnosti.preferences.UpdateHashPreference;

import static sk.zawy.lahodnosti.login.Profil.USER_ID;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_CREATED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_ID;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_COUNT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_ID_EVENT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PUBLISHED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_RESERVATION_KIND;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_UPDATED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_VISIBILITY;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_MENU;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_PERSONS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_TEXTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_USERS;

public class FirebaseDB {
    private Context context=null;
    public static final String AUTHORIZATION = "authorization";
    public static final String READ_ALL = "read_all";
    public static final String READ_ALL_FOR_CREATE = "read_all_create";
    public static final String COUNT = "count_document";
    public static final String READ_ONLY_UPDATE = "read_update";
    public static final String WRITE = "write";
    public static final String UPDATE = "update";
    private static final String CREATE = "create";
    public static final String START_LISTENER = "listener";

    private FirebaseFirestore db=null;
    private  Map<String,Object>result=new HashMap<>();
    private int count;

    public FirebaseDB(Context context) {
        this.context=context;
        db = FirebaseFirestore.getInstance();
    }


    public Map setData(final String ACTION, Object object, final String TABLE) {

        switch (ACTION) {
             case AUTHORIZATION:
                isAuthorizationFirebase(TABLE,(String)object);
                break;
            case READ_ALL:
                return readFromFirebasePlus(TABLE, (List) object,null);
            case READ_ALL_FOR_CREATE:
                return readFromFirebasePlus(TABLE, (List) object,CREATE);
            case READ_ONLY_UPDATE:
                return readFromFirebaseOnlyUpdate(TABLE);
            case COUNT:
                countPersons((String)object);
                break;
            case UPDATE:
                update((Map<String,Object>)object, TABLE);
                break;
            case WRITE:
                saveToFirebase((Map<String,Object>)object, TABLE);
                break;
            case START_LISTENER:
                changeListener();
                break;
        }
        return null;
    }

    private void countPersons(String id) {
        count=0;

        Task<QuerySnapshot> tasks = db.collection(TB_PERSONS)
                .whereEqualTo(COLUMN_PERSON_ID_EVENT,id)
                .get();

        for (;!tasks.isComplete();){
            //wait
        }

        for (QueryDocumentSnapshot document : tasks.getResult()) {
            count+=Integer.parseInt((String)document.get(COLUMN_PERSON_COUNT));
        }

    }


    /** search user in db and save data to 'preferences'
    * if the user doesn't exist, he will create it */
    private void isAuthorizationFirebase(final String TABLE,String id){
        db.collection(TABLE).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()) {
                    Profil.getInstance().setProfilData(task.getResult().getData());
                }else{
                    // no exist in db, create new user to firebase
                    saveUserToFirebase( null, id, TB_USERS);
                    Profil.getInstance();
                }
            }
        });
    }

    /** save user or person_event to db
     * @param object null - new user
     *               map<String,Object> if user insert user's details
     * @param id user id
     * */
    private void saveUserToFirebase(Map object, final String id, final String TABLE) {

        if(object==null) {
            object=new HashMap<String,Object>();
            object.put("authorization", "user");
            object.put(USER_ID, id);
        }

        db.collection(TABLE)
                .document(id)
                .set(object)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("logData", "FIREBASE DB- WRITTEN: " + id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("logData", "FIREBASE DB- WAS NOT WRITTEN!!! : " + id + " - " + e);
                    }
                });
    }


    private Map readFromFirebaseOnlyUpdate(final String TABLE){
        Map<String,String>idAndUpDate=new HashMap<>();

        Task<QuerySnapshot> tasks;

        switch (TABLE){
            case TB_MENU:
                tasks = db.collection(TABLE).orderBy(COLUMN_ID, Query.Direction.DESCENDING).limit(15).get();
                break;
            case TB_EVENTS:
                if(new UpdateHashPreference(context).loadData(UpdateHashPreference.HASH_CODE_DME_FOR_APP).equals("")){
                    tasks = db.collection(TABLE).orderBy(COLUMN_ID, Query.Direction.DESCENDING).limit(100).get();
                }else {
                    tasks = db.collection(TABLE).orderBy(COLUMN_ID, Query.Direction.DESCENDING).limit(25).get();
                }
                break;
            default:
                tasks = db.collection(TABLE).get();
                break;
        }

        for (;!tasks.isComplete();){
            //wait
        }

        for (QueryDocumentSnapshot document : tasks.getResult()) {
            idAndUpDate.put(document.getId(), TextEdit.notNull((String)document.get(COLUMN_UPDATED)));
            Log.d("logData", "FIREBASE DB- READ FOR DIFFERENT : OK  => " + document.getId() + " => " + TextEdit.notNull((String)document.get(COLUMN_UPDATED)));
        }

        return idAndUpDate;

    }



    /** load data from firebase database where input are IDs */
    private Map readFromFirebasePlus(final String TABLE, List IDs, String where){

        List<Object>tenList=new ArrayList();

        //IDs for read , select 10 item from Firebase whereIn (because 'whereIn' can contain max 10 param)
        if(IDs.size()>10){

            for(int x=0; x!=IDs.size(); x++){

                if(tenList.size()==10){

                    firestoreReadWhereIn(TABLE,tenList,where);
                    tenList.clear();
                }
                tenList.add(IDs.get(x));
            }
            if(tenList.size()>0) {
                firestoreReadWhereIn(TABLE, tenList, where);
                tenList.clear();
            }

        }else {
            firestoreReadWhereIn(TABLE,IDs,where);
            return getResult();
        }

        return result;

    }

    /** Read database with arg
     * access to the resulting values from another class - getResult
     * @param TABLE - database table
     * @param IDs -List of IDs after compare databases
     * @param where - null = request for Update SQLite database
     *              - CREATE = ...create to SQLite database
     * @return -void, values in format-Map<String,Map> are saved to 'result'
     * */
    private void firestoreReadWhereIn(String TABLE, List<Object> IDs,String where) {
        if(IDs.size()>0) {
            Task<QuerySnapshot> tasks=null ;

            if (where == null) {

                tasks = db.collection(TABLE)
                        .whereIn(com.google.firebase.firestore.FieldPath.documentId(), IDs)
                        .get();
            }else if(where.equals(CREATE))  {

                tasks = db.collection(TABLE)
                        .whereIn(com.google.firebase.firestore.FieldPath.documentId(), IDs)
                        .whereEqualTo(COLUMN_PUBLISHED,1)
                        .get();
            }

            for (; !tasks.isComplete(); ) {
                //wait
            }

            for (QueryDocumentSnapshot document : tasks.getResult()) {
                if(TABLE==TB_EVENTS && where!=null){
                    if(Profil.getInstance().isVip()){
                        if(document.getData().get(COLUMN_VISIBILITY).toString().equals("1")){
                            getResult().put(document.getId(), document.getData());
                        }
                    }else{      // for user no VIP
                        if(document.getData().get(COLUMN_VISIBILITY).toString().equals("1")){
                                getResult().put(document.getId(), document.getData());


                        }
                    }
                }else if(TABLE==TB_TEXTS && where!=null){
                       getResult().put(document.getId(), document.getData());
                }else if(TABLE==TB_PERSONS){
                    setResult(document.getData());
                }else{
                    getResult().put(document.getId(), document.getData());
                }

            }
        }

    }


    private void update(Map object, String TABLE) {
        String id="";
        switch (TABLE){
            case TB_USERS:
                id=(String)object.get(USER_ID);
                break;
            case TB_PERSONS:
                id=String.valueOf((String)object.get(COLUMN_PERSON_ID_EVENT)+(String)object.get(COLUMN_RESERVATION_KIND));
                object.put(COLUMN_UPDATED,timestamp());
                break;
        }

        db.collection(TABLE)
                .document(id)
                .update(object);
    }


    private void saveToFirebase(Map<String, Object> object, final String TABLE) {

        String id=String.valueOf((String)object.get(COLUMN_PERSON_ID_EVENT)+(String)object.get(COLUMN_RESERVATION_KIND));
        object.put(COLUMN_ID,id);
        object.put(COLUMN_CREATED,timestamp());
        object.put(COLUMN_UPDATED,timestamp());

        db.collection(TABLE)
                .document(id)
                .set(object)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("logData", "ORDER WAS MADE");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("logData", "ORDER WAS NOT MADE -" + e);
                    }
                });
    }

    private void changeListener(){
        DocumentReference documentReference=db.collection("updateData").document("DMEapp");
        ListenerRegistration registrationEvent=documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.err.println("Listen failed: " + error);
                    Log.d("logData","Listener error " +error);
                    return;
                }
                if (value != null && value.exists()) {
                    System.out.print("loading Firestore");
                    //Compare sharepreference HashCode of last updade and HashCode actual update from server
                    String beforeHashCode=new UpdateHashPreference(context).loadData(UpdateHashPreference.HASH_CODE_DME_FOR_APP);

                    if(beforeHashCode.equals(String.valueOf(value.getData().hashCode()))){
                        //No need update database, it is asctual
                        Log.d("logData","Listener all actual, is't work");
                    }else{
                        //Need synch SQLite and Firestore
                        Log.d("logData","Listener found differences");
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new TaskSynchDB(context).execute();
                            }
                        },10000);

                        new UpdateHashPreference(context).saveData(UpdateHashPreference.HASH_CODE_DME_FOR_APP, String.valueOf(value.getData().hashCode()));
                    }
                } else {
                    System.out.print("Current data: null");
                    Log.d("logData","Listener null");
                }
            }

        });

        Profil.getInstance().setListenerRegistration(registrationEvent); //registration listener


    }


    private String timestamp(){
        Date date=Timestamp.now().toDate();

        String timestamp=String.format("%s-%s-%s %s:%s:%s",
                (date.getYear()+1900),
                (String.valueOf(date.getMonth()+1).length()==1 ? String.valueOf("0"+ (date.getMonth()+1)) : String.valueOf(date.getMonth()+1)),
                String.valueOf(date.getDate()).length()==1 ? String.valueOf("0"+ (date.getDate())) : String.valueOf(date.getDate()),
                String.valueOf(date.getHours()).length()==1 ? String.valueOf("0"+ (date.getHours())) : String.valueOf(date.getHours()),
                String.valueOf(date.getMinutes()).length()==1 ? String.valueOf("0"+ (date.getMinutes())) : String.valueOf(date.getMinutes()),
                String.valueOf(date.getSeconds()).length()==1 ? String.valueOf("0"+ (date.getSeconds())) : String.valueOf(date.getSeconds())
        );


        return timestamp;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }
}
