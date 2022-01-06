package sk.zawy.lahodnosti.synch;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sk.zawy.lahodnosti.firebase.FillCollectionFromServer;
import sk.zawy.lahodnosti.sqlite.FillMapSQLite;

public class DataForCompare {
    private Context context;
    private String TABLE;

    private Map<String,String> sqliteDB=null;
    private Map<String,String> firebaseDB=null;
    private Set<String> updateToSQLite;
    private Set<String> createToSQLite;

    public DataForCompare(Context context, String TABLE) {
        this.context=context;
        this.TABLE = TABLE;
        sqliteDB=new FillMapSQLite(context,TABLE).sqliteData();
        firebaseDB= new FillCollectionFromServer(context,TABLE).getData();

        updateToSQLite=new  HashSet<>();
        createToSQLite=new  HashSet<>();

        compare();
        synchToSQL();
    }


    private void compare(){
        String key;
        for(Map.Entry entry: firebaseDB.entrySet()){
            key=(String)entry.getKey();
            if(sqliteDB.containsKey(key)){
                if(firebaseDB.get(key).length()>0) {

                    if (!firebaseDB.get(key).equals(sqliteDB.get(key))){
                        updateToSQLite.add(key);
                    }
                }
            }else{
                createToSQLite.add(key);
            }

        }

    }

    private void synchToSQL() {
        new SynchDB(context,TABLE).startSynch(new ArrayList<>(createToSQLite),new ArrayList<>(updateToSQLite));
    }

}
