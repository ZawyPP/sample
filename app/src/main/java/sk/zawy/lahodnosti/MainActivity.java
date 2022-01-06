package sk.zawy.lahodnosti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.SignInButton;

import sk.zawy.lahodnosti.asyncTasks.CheckVersion;
import sk.zawy.lahodnosti.asyncTasks.TaskJSONposts;
import sk.zawy.lahodnosti.asyncTasks.TaskSynchDB;
import sk.zawy.lahodnosti.facebookPosts.JSONfromFB;
import sk.zawy.lahodnosti.firebase.FirebaseDB;
import sk.zawy.lahodnosti.login.LoginGmail;

import sk.zawy.lahodnosti.animate.SplashAnimation;
import sk.zawy.lahodnosti.notification.MyFirebaseMessagingService;
import sk.zawy.lahodnosti.view.TabLayout;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_USERS;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9;
    private SignInButton button;
    private LoginGmail loginSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
   //     new TaskSynchDB(this);

        loginSingleton =LoginGmail.getInstance(this);

        button=(SignInButton) findViewById(R.id.buttonSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = loginSingleton.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Authorization true/false
        if(loginSingleton.onResult(requestCode,data)){
            if(loginSingleton.getCurrentUser()!=null){
                start(); //create/load profil
            }
        }else{
            AlertDialog dialog=new AlertDialog.Builder(this)
                    .setTitle(R.string.authorization_false)
                    .setMessage(R.string.authorization_false_message)
                    .setPositiveButton(R.string.repeat, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            signIn();
                        }
                    }).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(loginSingleton.getCurrentUser()!=null){
            start();
        }
    }

    private void start() {
        // Search User in database, when no exist will add to database and load profil
        button.setVisibility(View.GONE);

        TabLayout.position=0;
        FirebaseDB db=new FirebaseDB(this);

        db.setData(FirebaseDB.AUTHORIZATION,loginSingleton.getCurrentUser().getEmail(),TB_USERS);

        new SplashAnimation(this);

    }

}