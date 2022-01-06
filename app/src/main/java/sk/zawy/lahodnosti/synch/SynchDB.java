package sk.zawy.lahodnosti.synch;

import android.content.Context;
import android.util.Log;

import java.util.List;

import sk.zawy.lahodnosti.firebase.FirebaseDB;
import sk.zawy.lahodnosti.firebase.FirebaseToSQLite;
import sk.zawy.lahodnosti.firebase.FirebaseToSQLiteUpdate;

public class SynchDB {
    private Context context;
    private String TABLE;

    public SynchDB(Context context, String TABLE) {
        this.context = context;
        this.TABLE = TABLE;
    }

    public void startSynch(List<String> queryForCreate, List<String> queryForUpdate) {
        if(queryForCreate.size()!=0) {
            create(queryForCreate);
        }
        if(queryForUpdate.size()!=0) {
            update(queryForUpdate);
        }

    }

    private void create(List queryForCreate) {

        FirebaseDB db=new FirebaseDB(context);
        db.setData(FirebaseDB.READ_ALL_FOR_CREATE,queryForCreate,TABLE);

        FirebaseToSQLite dbToDB = new FirebaseToSQLite(context, TABLE, db.getResult());
    }


    private void update(List queryForUpdate){
        FirebaseDB db=new FirebaseDB(context);
        db.setData(FirebaseDB.READ_ALL,queryForUpdate,TABLE);

        FirebaseToSQLiteUpdate dbToDB = new FirebaseToSQLiteUpdate(context, TABLE, db.getResult());
    }


}
