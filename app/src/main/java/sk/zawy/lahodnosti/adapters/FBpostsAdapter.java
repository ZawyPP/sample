package sk.zawy.lahodnosti.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.holder.ViewHolderPostRow;
import sk.zawy.lahodnosti.objects.FBpost;

public class FBpostsAdapter extends ArrayAdapter<FBpost>  implements AdapterView.OnItemClickListener{
    private Context context;
    private List<FBpost> data;
    private View row=null;
    private ViewHolderPostRow holder;
    private ListView listView;

    public FBpostsAdapter(@NonNull Context context, int resource, @NonNull List data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        row=inflater.inflate(R.layout.row_fb_post,parent,false);

        holder=(ViewHolderPostRow) row.getTag();
        if(holder==null){
            holder=new ViewHolderPostRow(row);
            row.setTag(holder);
        }else{
            row.getTag();
        }

        holder.time.setText(data.get(position).getCreated_time().substring(0,data.get(position).getCreated_time().length()-8).replace("T","   "));

        if(data.get(position).getMessage().length()!=0) {
            holder.message.setText(data.get(position).getMessage());
        }else{
            holder.message.setVisibility(View.GONE);
        }

        if(data.get(position).getDescription().length()!=0) {
            holder.descrition.setText(data.get(position).getDescription());
        }else{
            holder.descrition.setVisibility(View.GONE);
        }

        if(data.get(position).getStory().length()!=0) {
            holder.story.setText(data.get(position).getStory());
            holder.descrition.setVisibility(View.GONE);
            holder.setRadius(ViewHolderPostRow.NEW_STORY);
        }else{
            holder.story.setVisibility(View.GONE);
        }

        if(data.get(position).getCaption().length()!=0) {
            holder.caption.setText(data.get(position).getCaption());
        }else{
            holder.caption.setVisibility(View.GONE);
        }


        return row;
    }



    public void setListView(ListView listView){
        this.listView = listView;
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+data.get(position).getId()));
        context.startActivity(intent);
    }
}
