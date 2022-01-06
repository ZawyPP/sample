package sk.zawy.lahodnosti.mySQL;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import sk.zawy.lahodnosti.accessories.TextEdit;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_UPDATED;

public class FillMapMySQL {
    private String TABLE;
    private Context context;

    public FillMapMySQL(Context context, final String TABLE) {
        this.context = context;
        this.TABLE = TABLE;
    }


    public Map mySqlData(){
        Map<String,String>data=new HashMap<>();
        try {
            Connect connect = new Connect(context);
            connect.createConnect();

            Statement statement = connect.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(new Select().getQueryTwoParam(TABLE));

            while(resultSet.next()){
                data.put(resultSet.getString("id"),
                        TextEdit.notNull(resultSet.getString(COLUMN_UPDATED)));
                Log.d("logData","MySQL napln : " + (resultSet.getString("id")));

            }

            statement.close();
            resultSet.close();
            connect.getConnection().close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        } catch (NullPointerException e){
            //CRASH SQL CONNECTION
            e.printStackTrace();
        }
        return data;
    }
}
