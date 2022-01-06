package sk.zawy.lahodnosti.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.view.NavigationDrawer;
import sk.zawy.lahodnosti.view.TabLayout;

public class ActivityContact extends AppCompatActivity implements View.OnClickListener {

    private NavigationDrawer navigationView;

    private TextView email1;
    private TextView email2;
    private TextView tel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=new NavigationDrawer(this);

        tel1=(TextView)findViewById(R.id.phoneTwo);
        email1=(TextView)findViewById(R.id.emailOne);
        email2=(TextView)findViewById(R.id.emailTwo);
        tel1.setOnClickListener(this);
        email1.setOnClickListener(this);
        email2.setOnClickListener(this);

        TabLayout tabLayout=new TabLayout(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(navigationView.toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){

            case R.id.phoneTwo:
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setMessage(R.string.call_lah)
                        .setPositiveButton("Áno", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel1.getText().toString()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Zrušiť",null).show();

                break;
            case R.id.emailOne:
                intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",email1.getText().toString(), null));
                startActivity(Intent.createChooser(intent, "Send email..."));
                break;
            case R.id.emailTwo:
                intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",email2.getText().toString(), null));
                startActivity(Intent.createChooser(intent, "Send email..."));
                break;
        }

    }
}