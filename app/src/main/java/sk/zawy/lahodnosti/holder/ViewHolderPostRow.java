package sk.zawy.lahodnosti.holder;

import android.content.Context;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.LinearLayout;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.view.DrawableRadius;

public class ViewHolderPostRow {
    public static final String NEW_STORY="post_story_event";
    private Context context;
    public TextView time;
    public TextView story;
    public TextView caption;
    public TextView descrition;
    public TextView message;
    private LinearLayout linearLayout;

    public ViewHolderPostRow(View base) {
        context=base.getContext();
        time=(TextView)base.findViewById(R.id.postTime);
        story=(TextView)base.findViewById(R.id.postStory);
        caption=(TextView)base.findViewById(R.id.postCaption);
        descrition=(TextView)base.findViewById(R.id.postDescription);
        message=(TextView)base.findViewById(R.id.postMessage);
        linearLayout=(LinearLayout) base.findViewById(R.id.layPost);

        new DrawableRadius(context, linearLayout, 35f).setBackgroundAndRadius(android.R.color.white, 25f, 10f, 10f, 25f);

    }

    public void setRadius(final String POST_TYPE){
        switch (POST_TYPE){
            case NEW_STORY:
                new DrawableRadius(context, linearLayout, 35f).setBackgroundAndRadius(R.color.postStory, 30f, 30f, 30f, 30f);
                break;
        }

    }
}
