package sk.zawy.lahodnosti.mySQL;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;

public class Sum {
    public static final int EVENT_SUM_RESERVATION=0;
    private Context context;
    private int occupancy=0;

    public Sum(Context context, int WHAT_SUM, String id) {
        this.context=context;

        switch (WHAT_SUM){
            case EVENT_SUM_RESERVATION:
                startSum(new Select().getSumForId(TB_EVENTS,id));
                break;
        }
    }

    private void startSum(String querySumForId) {

        Connect connect=new Connect(context);
        connect.createConnect();

        try {
            Statement statement=connect.getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(querySumForId);

            while (resultSet.next()){
                occupancy= resultSet.getInt(1);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                connect.getConnection().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public int getOccupancy() {
        return occupancy;
    }
}
