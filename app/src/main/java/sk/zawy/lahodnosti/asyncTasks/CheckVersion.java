package sk.zawy.lahodnosti.asyncTasks;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CheckVersion extends AsyncTask<Void,Void,String> {

    private Context context;
    private String currentVersion="";

    public CheckVersion(Context context) {
        this.context=context;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            currentVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String newVersion = null;

        try {

            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "sk.zawy.lahodnosti"  + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            if (document != null) {
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements sibElemets = ele.siblingElements();
                        for (Element sibElemet : sibElemets) {
                            newVersion = sibElemet.text();
                            Log.d("logData","GOOGLE PLAY verzia je: " + newVersion);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newVersion;

    }


    @Override
    protected void onPostExecute(String onlineVersion) {
        super.onPostExecute(onlineVersion);
        if (!currentVersion.equalsIgnoreCase(onlineVersion)) {
            //show dialog
            new AlertDialog.Builder(context)
                    .setTitle("Updated app available!")
                    .setMessage("Want to update app?")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                Toast.makeText(context, "App is in BETA version cannot update", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=sk.zawy.lahodnosti&hl=sk")));

                            } catch (ActivityNotFoundException anfe) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    })
                    .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }
}
