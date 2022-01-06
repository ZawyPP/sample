package sk.zawy.lahodnosti.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.DateCompare;
import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.holder.ViewHolderEventAdapter;
import sk.zawy.lahodnosti.objects.Event;
import sk.zawy.lahodnosti.popUp.PopUpDetail;

public class EventAdapter extends ArrayAdapter<Event> implements AdapterView.OnItemClickListener {
    private Context context;
    private List<Event>data;
    private View row=null;
    private ViewHolderEventAdapter holder;
    private Picasso picasso;
    private ListView listView;

    public EventAdapter(@NonNull Context context, int resource, @NonNull List data) {

        super(context, resource, data);
        this.context = context;
        this.data = data;

        picasso = new Picasso.Builder(context)
                .memoryCache(new LruCache(16000))
                .build();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row=inflater.inflate(R.layout.row_event,parent,false);

        holder=(ViewHolderEventAdapter)row.getTag();

        if(holder==null){
            holder=new ViewHolderEventAdapter(row);
            row.setTag(holder);
        }else{
            row.getTag();
        }

        holder.setData(data.get(position));

        holder.name.setText(data.get(position).getName());
        if(data.get(position).getTime2().length()>0){
            holder.date.setText(TextEdit.localDateFormat(data.get(position).getDate(), TextEdit.DAY_MONTH_DAYNAME) + " od " + data.get(position).getTime() +
                    " do " + data.get(position).getTime2());
        }else{
            holder.date.setText(TextEdit.localDateFormat(data.get(position).getDate(),  TextEdit.DAY_MONTH_DAYNAME) + " o " + data.get(position).getTime());
        }
        holder.kind.setText(data.get(position).getKind());
        holder.price1.setText(data.get(position).getPrice() + "€ ");
        holder.price2.setText(data.get(position).getPrice2() + "€ ");

        if(data.get(position).getPrice().equals("0")){
            holder.price1.setText(context.getResources().getText(R.string.voluntary_price));
            holder.price1.setTypeface(holder.price1.getTypeface(), Typeface.NORMAL);
            holder.priceTextView1.setVisibility(View.GONE);
            holder.priceTextView.setVisibility(View.INVISIBLE);
            holder.price2.setVisibility(View.INVISIBLE);
        }
        if(data.get(position).getPrice2().length()<1||
                data.get(position).getPrice2().equals("0")){
            holder.priceTextView.setVisibility(View.INVISIBLE);
            holder.price2.setVisibility(View.INVISIBLE);
        }

        if(data.get(position).getUrl().length()>0) {
                picasso.get().load(data.get(position).getUrl()).into(holder.imageView);
        }else{
            picasso.get().load(TextEdit.changeAndSymbol(context.getResources().getString(R.string.no_event_photo))).into(holder.imageView);
            LinearLayout.LayoutParams l= (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();

            /* PHOTO NOT FOUND */
            l.height=300;
            holder.imageView.setLayoutParams(l);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER);
            holder.imageView.setAlpha(0.2F);
        }

        if(data.get(position).isEnable()==0) {
            holder.disable.setVisibility(View.VISIBLE);
            holder.item.setAlpha(0.4f);
        }

        if(data.get(position).isEnable()!=0) {
            try {
                DateCompare dateCompare = new DateCompare(Integer.parseInt(data.get(position).getTime().substring(0, data.get(position).getTime().indexOf(":"))),
                        Integer.parseInt(data.get(position).getTime().substring(data.get(position).getTime().indexOf(":") + 1, data.get(position).getTime().indexOf(":") + 3)));
                if (dateCompare.isActual(data.get(position).getDate())) {
                    holder.imgTickets.setVisibility(View.VISIBLE);
                } else {
                    holder.imgTickets.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            holder.imgTickets.setVisibility(View.GONE);
        }
        switch(data.get(position).getKind()){
            case "KONCERT":
                holder.kind.setBackgroundResource(R.drawable.background_kind_green);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
            case "LITERATÚRA":
                holder.kind.setBackgroundResource(R.drawable.background_kind_yellow);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.black));
                break;
            case "KINO":
                holder.kind.setBackgroundResource(R.drawable.backgroung_kind_black);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
            case "DISKUSIA":
                holder.kind.setBackgroundResource(R.drawable.background_kind_red);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
            case "WORKSHOP":
                holder.kind.setBackgroundResource(R.drawable.background_kind_brown);
                holder.kind.setTextColor(context.getResources().getColor(android.R.color.white));
                break;
        }
        return row;
    }


    public void setListView(ListView listView){
        this.listView = listView;
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        new PopUpDetail(context,PopUpDetail.POPUP_EVENT_INFO,data.get(position));
    }
}
