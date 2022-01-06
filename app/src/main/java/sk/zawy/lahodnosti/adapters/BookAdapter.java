package sk.zawy.lahodnosti.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.List;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.animate.ViewSetAlpha;
import sk.zawy.lahodnosti.holder.ViewHolderBookAdapter;
import sk.zawy.lahodnosti.objects.BookItem;

public class BookAdapter extends ArrayAdapter<BookItem> implements AdapterView.OnItemClickListener {
    private Context context;
    private List<BookItem>data=null;
    private View row=null;
    private ViewHolderBookAdapter holder;
    private Picasso picasso;
    private ListView listView;

    private TextView name;
    private TextView about;
    private TextView q1;
    private TextView q2;
    private TextView q3;
    private TextView q4;
    private ImageView logo;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private LinearLayout dataBrowser,titleScreen;
    private ScrollView scrollView;


    public BookAdapter(@NonNull Context context, int resource, @NonNull List data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        View view = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        about=(TextView)view.findViewById(R.id.bAbout);
        name=(TextView)view.findViewById(R.id.bTitle);
        about=(TextView)view.findViewById(R.id.bAbout);
        q1=(TextView)view.findViewById(R.id.bQuestion1);
        q2=(TextView)view.findViewById(R.id.bQuestion2);
        q3=(TextView)view.findViewById(R.id.bQuestion3);
        q4=(TextView)view.findViewById(R.id.bQuestion4);
        img1=(ImageView)view.findViewById(R.id.bImg1);
        img2=(ImageView)view.findViewById(R.id.bImg2);
        img3=(ImageView)view.findViewById(R.id.bImg3);
        img4=(ImageView)view.findViewById(R.id.bImg4);
        dataBrowser=(LinearLayout)view.findViewById(R.id.bookData);
        titleScreen=(LinearLayout)view.findViewById(R.id.booktitleScreen);
        scrollView=(ScrollView)view.findViewById(R.id.book_scroll) ;

        dataBrowser.setVisibility(View.GONE);
        titleScreen.setVisibility(View.VISIBLE);

        picasso = new Picasso.Builder(context)
                .memoryCache(new LruCache(16000))
                .build();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_book_logo, parent, false);

        holder = (ViewHolderBookAdapter) row.getTag();

        if (holder == null) {
            holder = new ViewHolderBookAdapter(context, row);
            row.setTag(holder);
        } else {
            row.getTag();
        }
        if(data.get(position).getLogo()!=null) {
            if (data.get(position).getLogo().length() > 0) {
                picasso.get().load(data.get(position).getLogo())
                        .priority(Picasso.Priority.HIGH)
                        .resize(100,100)
                        .into(holder.logo);
            }else{
                //in DB isn't Logo pictures
                holder.logo.setAlpha(0.1f);
                holder.noLogo.setVisibility(View.VISIBLE);
                holder.noLogo.setText(data.get(position).getName());
                picasso.get().load(TextEdit.changeAndSymbol(context.getResources().getString(R.string.no_event_photo)))
                        .priority(Picasso.Priority.HIGH)
                        .resize(100,100)
                        .into(holder.logo);
            }
        }

        return row;
    }

    public void setListView(ListView listView){
        this.listView = listView;
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        new ViewSetAlpha(dataBrowser,0.0f,0.0f,500).after(0.0f,1.0f,900);
        dataBrowser.setVisibility(View.VISIBLE);
        titleScreen.setVisibility(View.GONE);

        scrollView.fullScroll(ScrollView.FOCUS_UP);

        name.setText(data.get(position).getName());

        if(data.get(position).getAbout().length()>0){
            about.setVisibility(View.VISIBLE);
            about.setText(data.get(position).getAbout().toUpperCase());
        }else {
            about.setVisibility(View.GONE);
        }

        q1.setText(data.get(position).getQ1().toUpperCase());
        q2.setText(data.get(position).getQ2().toUpperCase());
        q3.setText(data.get(position).getQ3().toUpperCase());
        q4.setText(data.get(position).getQ4().toUpperCase());

        setImage(data.get(position).getImg1(),img1);
        setImage(data.get(position).getImg2(),img2);
        setImage(data.get(position).getImg3(),img3);
        setImage(data.get(position).getImg4(),img4);
    }

    private void setImage(String url, ImageView img){
        if(url.length()>0) {
            img.setVisibility(View.VISIBLE);
            picasso.get().load(url).into(img);
        }else {
            img.setVisibility(View.GONE);
        }
    }
}
