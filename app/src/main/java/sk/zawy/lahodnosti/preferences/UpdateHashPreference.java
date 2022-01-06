package sk.zawy.lahodnosti.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class UpdateHashPreference {

    private final String PREFERENCE="updateHashCode";
    public static final String HASH_CODE_DME_FOR_APP="hashCodeDMEapp";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UpdateHashPreference(Context context) {
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
