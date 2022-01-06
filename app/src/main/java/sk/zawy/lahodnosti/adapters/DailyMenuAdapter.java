package sk.zawy.lahodnosti.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.DateCompare;
import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.holder.ViewHolderDailyMenuAdapter;
import sk.zawy.lahodnosti.objects.DailyMenu;

public class DailyMenuAdapter extends ArrayAdapter<DailyMenu>  {
    private Context context;
    private List<DailyMenu> data;
    private View row=null;
    private ViewHolderDailyMenuAdapter holder;

    public DailyMenuAdapter(@NonNull Context context, int resource, @NonNull List data) {

        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row=inflater.inflate(R.layout.row_daily_menu,parent,false);

        holder=(ViewHolderDailyMenuAdapter) row.getTag();

        if(holder==null){
            holder=new ViewHolderDailyMenuAdapter(row);
            row.setTag(holder);
        }else{
            row.getTag();
        }

        holder.setData(data.get(position));
        holder.date.setText(TextEdit.localDateFormat(data.get(position).getId(), TextEdit.DAY_MONTH) + " " + data.get(position).getDay());
        holder.soup.setText(data.get(position).getSoup());
        holder.mainMeal.setText(data.get(position).getMailMeal());

        /* Ticket icon show between open/close time*/
        if(holder.mainMeal.getText().toString().length()>7) {
            try {
                DateCompare dateCompare = new DateCompare(14, 00);
                if (dateCompare.isActual(data.get(position).getId())) {
                    holder.imgTickets.setVisibility(View.VISIBLE);
                } else {
                    holder.imgTickets.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            holder.imgTickets.setVisibility(View.INVISIBLE);
        }
        return row;
    }


}