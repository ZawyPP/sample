package sk.zawy.lahodnosti.accessories;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import sk.zawy.lahodnosti.R;

public class Call {

    private Activity activity;
    private final int PERM_CALL = 5;
    private Uri phoneNo;

    public Call(Context context, String phone) {
        this.activity = (Activity)context;
        this.phoneNo = Uri.parse("tel:" + phone);
        getPermission();
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity,activity.getResources().getString(R.string.call_permission),Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERM_CALL);
        }else{
            startCall();
        }
    }

    private void startCall() {
        try {
            Intent callNow = new Intent(Intent.ACTION_CALL, phoneNo);
            activity.startActivity(callNow);
        }catch (Exception e){
            Log.d("logData","Error Call.class --> startCall() ! " + e);
            Toast.makeText(activity,activity.getResources().getString(R.string.call_error),Toast.LENGTH_LONG).show();
        }
    }
}

