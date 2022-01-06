package sk.zawy.lahodnosti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.UseAdapter;
import sk.zawy.lahodnosti.animate.ViewSetAlpha;
import sk.zawy.lahodnosti.view.NavigationDrawer;
import sk.zawy.lahodnosti.view.TabLayout;

public class ActivityTexts extends AppCompatActivity {

    private NavigationDrawer navigationView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent intent=getIntent();
        final int TEXT_TYPE=intent.getIntExtra("text_type",0);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=new NavigationDrawer(this);
        listView=(ListView)findViewById(R.id.listView);

        new UseAdapter(listView, TEXT_TYPE); //listeners in the adapter
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