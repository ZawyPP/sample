package sk.zawy.lahodnosti.mySQL;

import java.util.Set;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_COUNT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_ID_EVENT;
import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_UPDATED;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_PERSONS;

public class Select {

    public String getQuery(String TABLE) {
        return "SELECT * FROM " + TABLE + " ; ";
    }

    public String getQueryTwoParam(String TABLE) {
        return "SELECT id , " + COLUMN_UPDATED + " FROM " + TABLE + " ; ";
    }

    public String getQueryForId(String TABLE, Set dataID){
        return "SELECT * FROM " + TABLE + " WHERE id IN ( " + getIdBuilder(dataID) + " ) ;";
    }

    public String getSumForId(String TABLE, String dataID){
        String column="";
        String table="";
        switch (TABLE){
            case TB_EVENTS:
                column=COLUMN_PERSON_COUNT;
                table=TB_PERSONS;
                break;
        }
        return "SELECT SUM(" + column + ") FROM " + table + " WHERE " + COLUMN_PERSON_ID_EVENT + " =" + dataID + "  ;";
    }


    private String  getIdBuilder(Set data){
        StringBuilder idCollection=new StringBuilder();
        for(String id : (Set<String>)data){
            if(idCollection.length()>0) {
                idCollection.append(" , \"" + id + "\"");
            }else{
                idCollection.append("\"" + id + "\"");
            }
        }
        return idCollection.toString();
    }
}
