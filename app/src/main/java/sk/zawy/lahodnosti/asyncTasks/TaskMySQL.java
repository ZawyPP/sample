package sk.zawy.lahodnosti.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sk.zawy.lahodnosti.mySQL.Connect;
import sk.zawy.lahodnosti.mySQL.MySQLtoSQLite;
import sk.zawy.lahodnosti.mySQL.Select;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_EVENTS;

public class TaskMySQL extends AsyncTask<Void,Void,Void> {
    private Context context;


    public TaskMySQL(Context context) {
        this.context=context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String TABLE=TB_EVENTS;

        try {
            Connect connect = new Connect(context);
            connect.createConnect();
            Statement statement = connect.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(new Select().getQuery(TABLE));
            MySQLtoSQLite dbToDB = new MySQLtoSQLite(context, TABLE, resultSet);

            statement.close();
            resultSet.close();
            connect.getConnection().close();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
