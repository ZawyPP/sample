package sk.zawy.lahodnosti.notification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.activities.ActivityBook;
import sk.zawy.lahodnosti.login.Profil;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

//        String title="title";
//        String body="body";

//        String title=remoteMessage.getNotification().getTitle();
//        String body=remoteMessage.getNotification().getBody();
//        final String CHANNEL_ID="HEADS_UP_NOTIFICATION";
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel= new NotificationChannel(
//                    CHANNEL_ID,
//                    "Heads Up Notification",
//                    NotificationManager.IMPORTANCE_HIGH);
//
//            getSystemService(NotificationManager.class).createNotificationChannel(channel);
//
//
//        Notification.Builder notification=new Notification.Builder(this, CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setSmallIcon(R.drawable.logo_lahodnosti)
//                .setAutoCancel(true);
//
//            NotificationManagerCompat.from(this).notify(1,notification.build());
//        }

        super.onMessageReceived(remoteMessage);
    }


    @Override
    public void onMessageSent(@NonNull String s) {

        super.onMessageSent(s);

    }

    public MyFirebaseMessagingService() {
        super();

    }
}
