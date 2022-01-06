package sk.zawy.lahodnosti.holder;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.UseAdapter;
import sk.zawy.lahodnosti.sqlite.Count;
import sk.zawy.lahodnosti.sqlite.PopulateFromSQLite;

public class ViewHolderDailyMenu2 implements View.OnClickListener {

    private TextView back;
    private TextView actual;
    private TextView next;
    private ListView listView;
    private int week=0;
    private Context context;


    public ViewHolderDailyMenu2(View base, ListView listView) {
        this.context=base.getContext();
        this.listView=listView;
        new UseAdapter(listView, PopulateFromSQLite.POPULATE_DAILY_MENU,week);
        back=(TextView)base.findViewById(R.id.lastWeek);
        actual=(TextView) base.findViewById(R.id.actualWeek);
        next=(TextView) base.findViewById(R.id.nextWeek);

        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lastWeek:
                week--;
                next.setVisibility(View.VISIBLE);
                if(new Count(context,Count.COUNT_DAILY_MENU,week-1).getCount()==0){
                    back.setVisibility(View.INVISIBLE);
                }
                if(week==-1){
                    actual.setText("Minulý týždeň");
                    actual.setVisibility(View.VISIBLE);
                    back.setVisibility(View.INVISIBLE);
                }else if(week==0) {
                    actual.setText("Aktuálny týždeň");
                    back.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                }else{
                    actual.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.actualWeek:
                week=0;
                next.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                break;
            case R.id.nextWeek:
                week++;

                back.setVisibility(View.VISIBLE);
                if(new Count(context,Count.COUNT_DAILY_MENU,week+1).getCount()==0){
                    next.setVisibility(View.INVISIBLE);
                }
                if(week==1){
                    actual.setText("Budúci týždeň");
                    actual.setVisibility(View.VISIBLE);
                    next.setVisibility(View.INVISIBLE);
                }else if(week==0) {
                    actual.setText("Aktuálny týždeň");
                    back.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                }else{
                    actual.setVisibility(View.INVISIBLE);
                }
                break;
        }
        new UseAdapter(listView, PopulateFromSQLite.POPULATE_DAILY_MENU,week);
    }
}
