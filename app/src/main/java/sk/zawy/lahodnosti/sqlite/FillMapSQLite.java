package sk.zawy.lahodnosti.sqlite;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

import sk.zawy.lahodnosti.accessories.TextEdit;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_ID;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_UPDATED;

public class FillMapSQLite {
    private String TABLE;
    private Context context;

    public FillMapSQLite(Context context, String TABLE) {
        this.context=context;
        this.TABLE=TABLE;
    }

    public Map sqliteData(){
        Map<String,String>data=new HashMap<>();
        Select select=new Select(context);
        Cursor cursor= select.dataMap(TABLE);

        if(cursor!=null){
            while(cursor.moveToNext()){
                data.put(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        TextEdit.notNull(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED))));

            }
            cursor.close();
            select.close();
        }

        return data;
    }
}
