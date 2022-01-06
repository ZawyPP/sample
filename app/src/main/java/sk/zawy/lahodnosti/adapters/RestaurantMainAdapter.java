package sk.zawy.lahodnosti.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sk.zawy.lahodnosti.holder.ViewHolderRestaurantMain;
import sk.zawy.lahodnosti.objects.Texts;

public class RestaurantMainAdapter extends ArrayAdapter <Texts> {

    Context context;
    List<Texts> data;
    View row=null;
    ViewHolderRestaurantMain holder;
    int resource;

    public RestaurantMainAdapter(@NonNull Context context, int resource, @NonNull List data) {
        super(context, resource, data);
        this.resource = resource;
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row=inflater.inflate(resource,parent,false);

        holder=(ViewHolderRestaurantMain)row.getTag();

        if(holder==null){
            holder=new ViewHolderRestaurantMain(row);
            row.setTag(holder);
        }else{
            row.getTag();
        }

        holder.setText1(data.get(position).getText1());
        holder.setText2(data.get(position).getText2());
        holder.setText3(data.get(position).getText3());
        holder.setText4(data.get(position).getText4());
        holder.setText5(data.get(position).getText5());

        holder.setImg1(data.get(position).getImg1());
        holder.setImg2(data.get(position).getImg2());
        holder.setImg3(data.get(position).getImg3());
        holder.setImg4(data.get(position).getImg4());

        return row;

    }

}
