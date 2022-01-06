package sk.zawy.lahodnosti.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import sk.zawy.lahodnosti.BuildConfig;
import sk.zawy.lahodnosti.MainActivity;
import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.activities.ActivityContact;
import sk.zawy.lahodnosti.activities.ActivityTexts;
import sk.zawy.lahodnosti.login.LoginGmail;
import sk.zawy.lahodnosti.login.Profil;
import sk.zawy.lahodnosti.popUp.PopUpOrdersList;
import sk.zawy.lahodnosti.popUp.PopUpProfil;
import sk.zawy.lahodnosti.sqlite.PopulateFromSQLite;

public class NavigationDrawer {

    public ActionBarDrawerToggle toggle;
    public DrawerLayout drawerLayout;
    public NavigationView navView;
    private View base;
    private ListView listView;
    private TextView version;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationDrawer(Context context) {
        Activity activity= (Activity)context;
        base=activity.getWindow().getDecorView().findViewById(android.R.id.content);
        listView=(ListView)base.findViewById(R.id.listView);
        drawerLayout=(DrawerLayout)base.findViewById(R.id.navigation_drawer);

        toggle=new ActionBarDrawerToggle(activity,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView=(NavigationView)base.findViewById(R.id.navView);

        View headerView=navView.getHeaderView(0);
        version=(TextView)headerView.findViewById(R.id.version);
        version.setText(context.getString(R.string.version) + BuildConfig.VERSION_NAME);
        Profil.getInstance().profilND(headerView);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Intent intent=null;
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    TabLayout.position=0;
                    ((Activity) context).startActivity(intent);
                    ((Activity) context).overridePendingTransition(0,0);
                    ((Activity) context).finish();
                }
            };

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.aboutus:
                        intent=new Intent(((Activity) context), ActivityTexts.class);
                        intent.putExtra("text_type",PopulateFromSQLite.POPULATE_TEXTS_NEW);
                        handler.postDelayed(runnable,200);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.contact:
                        intent=new Intent(((Activity) context), ActivityContact.class);
                        handler.postDelayed(runnable,200);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profil_in_menu:
                        new PopUpProfil(context);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.my_orders:
                        new PopUpOrdersList(context);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.oou:
                        Intent intentNet=new Intent(Intent.ACTION_VIEW);
                        intentNet.setData(Uri.parse(context.getResources().getString(R.string.OOU)));
                        context.startActivity(intentNet);
                        break;
                    case R.id.menuSignOut:
                        //logOut User and preferences data load default
                        LoginGmail.getInstance(context).signOut();
                        Profil.getInstance().kill();
                        Intent intent= new Intent(((Activity) context), MainActivity.class);
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();
                        break;

                }
                return false;
            }
        });
    }

}
