package sk.zawy.lahodnosti.mySQL;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import sk.zawy.lahodnosti.accessories.PropertyReader;

public class Connect {
    private Context context;
    private Connection connection=null;
    private PropertyReader propertyReader;
    private Properties properties;

    public Connect(Context context) {
        this.propertyReader=new PropertyReader(context);
        loadProperties();
    }

    public void createConnect(){
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection= DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("pass"));

        } catch (SQLException e) {
            Log.d("logData","CHYBA SQL PRIPOJENIA" + e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Nacitanie parametrov k pristupu*/
    private void loadProperties() {
        properties = propertyReader.getMyProperties("valueForConnect.properties");
    }

    public Connection getConnection() {
        return connection;
    }
}
