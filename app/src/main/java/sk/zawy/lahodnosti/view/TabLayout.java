package sk.zawy.lahodnosti.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.activities.ActivityBook;
import sk.zawy.lahodnosti.activities.ActivityDailyMenu;
import sk.zawy.lahodnosti.activities.ActivityEvents;
import sk.zawy.lahodnosti.activities.ActivityTexts;
import sk.zawy.lahodnosti.popUp.PopUpOrdersList;
import sk.zawy.lahodnosti.sqlite.PopulateFromSQLite;

public class TabLayout {
    private Context context;
    private Intent intent=null;
    public static Integer position=0;

    public TabLayout(Context context) {
        this.context=context;

        tabLayout();
    }

    private void tabLayout() {
    com.google.android.material.tabs.TabLayout tabLayout=(com.google.android.material.tabs.TabLayout)((Activity) context).findViewById(R.id.tabLay);

        tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.colorPrimary));
        tabLayout.setTabTextColors(context.getResources().getColor(R.color.colorPrimary),context.getResources().getColor(R.color.tabSelectedTextColor));


        if(TabLayout.position!=0){
            switch (TabLayout.position) {
                case 1:
                    com.google.android.material.tabs.TabLayout.Tab tab1=tabLayout.getTabAt(0);
                    tab1.select();
                    break;
                case 2:
                    com.google.android.material.tabs.TabLayout.Tab tab2=tabLayout.getTabAt(1);
                    tab2.select();
                    break;
                case 3:
                    com.google.android.material.tabs.TabLayout.Tab tab3=tabLayout.getTabAt(2);
                    tab3.select();
                    break;
                case 4:
                    com.google.android.material.tabs.TabLayout.Tab tab4=tabLayout.getTabAt(3);
                    tab4.select();
                    break;
            }
        }else{
            tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.tabSelectedTextColor));
            tabLayout.setTabTextColors(context.getResources().getColor(R.color.colorPrimary),context.getResources().getColor(R.color.colorPrimary));

        }


        tabLayout.setOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {


            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    ((Activity) context).startActivity(intent);
                    ((Activity) context).overridePendingTransition(0,0);
                    ((Activity) context).finish();
                }
            };

            switch (tab.getPosition()){
                case 0:
                        //restauant
                    TabLayout.position=1;
                    intent=new Intent(((Activity) context), ActivityTexts.class);
                    intent.putExtra("text_type",PopulateFromSQLite.POPULATE_TEXTS_MAIN);
                    handler.postDelayed(runnable,200);

                    break;
                case 1:
                        //events
                    TabLayout.position=2;
                    intent=new Intent(((Activity) context), ActivityEvents.class);
                    handler.postDelayed(runnable,200);
                    break;
                case 2:
                        //dailymenu
                    TabLayout.position=3;
                    intent=new Intent(((Activity) context), ActivityDailyMenu.class);
                    handler.postDelayed(runnable,200);
                    break;
                case 3:
                       //book
                    TabLayout.position=4;
                    intent=new Intent(((Activity) context), ActivityBook.class);
                    handler.postDelayed(runnable,200);
                    break;
            }

        }

        @Override
        public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab){

        }

        @Override
        public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    ((Activity) context).startActivity(intent);
                    ((Activity) context).overridePendingTransition(0,0);
                    ((Activity) context).finish();
                }
            };

            switch (tab.getPosition()) {
                case 0:
                    TabLayout.position = 1;
                    intent = new Intent(((Activity) context), ActivityTexts.class);
                    intent.putExtra("text_type",PopulateFromSQLite.POPULATE_TEXTS_MAIN);
                    handler.postDelayed(runnable, 200);

                    break;
            }
        }
    });
}



}
