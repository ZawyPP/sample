package sk.zawy.lahodnosti.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Preferences {

    public static final String PREFERENCE="userValue";
    public static final String LAST_EDIT_ADDRESS="userLastAddress";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context context, final String PREFERENCE) {
        sharedPreferences=context.getSharedPreferences(PREFERENCE,MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public String loadData(final String PREFERENCE){
        return sharedPreferences.getString(PREFERENCE,"");
    }

    public void saveData(final String PREFERENCE, String value){
        editor.putString(PREFERENCE, value);
        editor.apply();
    }


}
