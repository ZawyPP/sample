package sk.zawy.lahodnosti.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import sk.zawy.lahodnosti.R;

public class ViewHolderRestaurantMain {

        private TextView text1;
        private TextView text2;
        private TextView text3;
        private TextView text4;
        private TextView text5;
        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private ImageView img4;

        private Picasso picasso;

        public ViewHolderRestaurantMain(View base) {
            text1=(TextView)base.findViewById(R.id.text1);
            text2=(TextView)base.findViewById(R.id.text2);
            text3=(TextView)base.findViewById(R.id.text3);
            text4=(TextView)base.findViewById(R.id.text4);
            text5=(TextView)base.findViewById(R.id.text5);

            img1=(ImageView)base.findViewById(R.id.img1);
            img2=(ImageView)base.findViewById(R.id.img2);
            img3=(ImageView)base.findViewById(R.id.img3);
            img4=(ImageView)base.findViewById(R.id.img4);

            picasso = new Picasso.Builder(base.getContext())
                    .memoryCache(new LruCache(16000))
                    .build();

        }


    public void setText1(String text) {
            if(text.length()>0){
                this.text1.setVisibility(View.VISIBLE);
            }
        this.text1.setText(text);
    }

    public void setText2(String text) {
        if(text.length()>0){
            this.text2.setVisibility(View.VISIBLE);
        }
        this.text2.setText(text);
    }

    public void setText3(String text) {
        if(text.length()>0){
            this.text3.setVisibility(View.VISIBLE);
        }
        this.text3.setText(text);
    }

    public void setText4(String text) {
        if(text.length()>0){
            this.text4.setVisibility(View.VISIBLE);
        }
        this.text4.setText(text);
    }

    public void setText5(String text) {
        if(text.length()>0){
            this.text5.setVisibility(View.VISIBLE);
        }
        this.text5.setText(text);
    }

    public void setImg1(String url) {
        if(url.length()>0){
            this.img1.setVisibility(View.VISIBLE);
            picasso.get().load(url).priority(Picasso.Priority.HIGH).into(img1);
        }
    }

    public void setImg2(String url) {
        if(url.length()>0){
            this.img2.setVisibility(View.VISIBLE);
            picasso.get().load(url).priority(Picasso.Priority.HIGH).into(img2);
        }
    }

    public void setImg3(String url) {
        if(url.length()>0){
            this.img3.setVisibility(View.VISIBLE);
            picasso.get().load(url).priority(Picasso.Priority.HIGH).into(img3);
        }
    }

    public void setImg4(String url) {
        if(url.length()>0){
            this.img4.setVisibility(View.VISIBLE);
            picasso.get().load(url).priority(Picasso.Priority.HIGH).into(img4);
        }
    }



}
