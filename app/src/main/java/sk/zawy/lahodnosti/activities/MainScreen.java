package sk.zawy.lahodnosti.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.UseAdapter;
import sk.zawy.lahodnosti.animate.ViewSetAlpha;
import sk.zawy.lahodnosti.asyncTasks.TaskJSONposts;
import sk.zawy.lahodnosti.asyncTasks.TaskSynchDB;
import sk.zawy.lahodnosti.firebase.FirebaseDB;
import sk.zawy.lahodnosti.login.Profil;
import sk.zawy.lahodnosti.notification.MyFirebaseMessagingService;
import sk.zawy.lahodnosti.objects.FBpost;
import sk.zawy.lahodnosti.popUp.PopUpFirstStart;
import sk.zawy.lahodnosti.sqlite.PopulateFromSQLite;
import sk.zawy.lahodnosti.view.NavigationDrawer;
import sk.zawy.lahodnosti.view.TabLayout;

public class MainScreen extends AppCompatActivity  {

    private NavigationDrawer navigationView;
    private ListView listView;
    private ImageView bgImage;
    private List<FBpost> data;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        bgImage=(ImageView)findViewById(R.id.imageViewBG);
        bgImage.setVisibility(View.VISIBLE);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=new NavigationDrawer(this);

        listView=(ListView)findViewById(R.id.listView);

        TabLayout tabLayout=new TabLayout(this);

        if(!Profil.getInstance().isVerification()){
            //start when User not verified
            new PopUpFirstStart(this);
        };

        new FirebaseDB(this).setData(FirebaseDB.START_LISTENER,null,null);
        new TaskJSONposts(this).execute();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(navigationView.toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dataForAdapter(List<FBpost> data){
        if(data!=null){
            this.data=data;
            listView.setDividerHeight(0);
            new UseAdapter(listView, data); //view title screen
        }else{
            new UseAdapter(listView, PopulateFromSQLite.POPULATE_TEXTS_MAIN); //view title screen
            listView.setOnItemClickListener(null);
        }

        new ViewSetAlpha(listView,0.0f,1.0f,900);
    }


}