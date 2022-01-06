package sk.zawy.lahodnosti.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.UseAdapter;
import sk.zawy.lahodnosti.animate.ViewSetAlpha;
import sk.zawy.lahodnosti.holder.ViewHolderBookAdapter;
import sk.zawy.lahodnosti.objects.BookItem;
import sk.zawy.lahodnosti.sqlite.PopulateFromSQLite;
import sk.zawy.lahodnosti.view.NavigationDrawer;
import sk.zawy.lahodnosti.view.TabLayout;

public class ActivityBook extends AppCompatActivity {

    private NavigationDrawer navigationView;
    private ListView listView;
    private LinearLayout bookLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=new NavigationDrawer(this);

        bookLinearLayout=(LinearLayout)findViewById(R.id.centerInclude2);
        listView=(ListView)bookLinearLayout.findViewById(R.id.listView);
        ViewHolderBookAdapter holder=new ViewHolderBookAdapter(ActivityBook.this,bookLinearLayout);
        List<BookItem> data=new PopulateFromSQLite(ActivityBook.this,PopulateFromSQLite.POPULATE_BOOK).getData();
        new UseAdapter(listView, PopulateFromSQLite.POPULATE_BOOK); //listeners in the adapter

        ImageView background=(ImageView)bookLinearLayout.findViewById(R.id.bookBackground);
        LinearLayout title=(LinearLayout)bookLinearLayout.findViewById(R.id.booktitleScreen);

        new ViewSetAlpha(title,0,0,900).after(0.0f,1.0f,700);
        new ViewSetAlpha(listView,0,0,800).after(0.0f,1.0f,600);
        new ViewSetAlpha(background,0.4f,.2f,800).after(.2f,.041f,8000);

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