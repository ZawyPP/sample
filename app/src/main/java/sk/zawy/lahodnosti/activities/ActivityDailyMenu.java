package sk.zawy.lahodnosti.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.animate.ViewSetAlpha;
import sk.zawy.lahodnosti.holder.ViewHolderDailyMenu2;
import sk.zawy.lahodnosti.view.NavigationDrawer;
import sk.zawy.lahodnosti.view.TabLayout;

public class ActivityDailyMenu extends AppCompatActivity {

    private NavigationDrawer navigationView;
    private ListView listView;
    private LinearLayout dailyMenuLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_menu);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=new NavigationDrawer(this);

        dailyMenuLinearLayout=(LinearLayout)findViewById(R.id.centerInclude3);
        listView=(ListView)dailyMenuLinearLayout.findViewById(R.id.listView);

        //adapter in the holder
        ViewHolderDailyMenu2 holder2=new ViewHolderDailyMenu2(dailyMenuLinearLayout,listView);
        new ViewSetAlpha(listView,0.0f,1.0f,900);
        TabLayout tabLayout=new TabLayout(this);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(navigationView.toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}