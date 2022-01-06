package sk.zawy.lahodnosti.firebase;

import android.content.Context;

import java.util.Map;

public class FillCollectionFromServer {
    private FirebaseDB db;
    private String TABLE;

    public FillCollectionFromServer(Context context, final String TABLE) {
        this.TABLE=TABLE;
        this.db=new FirebaseDB(context);
    }


    public Map<String, String> getData() {
        return db.setData(FirebaseDB.READ_ONLY_UPDATE, null, TABLE);

    }
}
