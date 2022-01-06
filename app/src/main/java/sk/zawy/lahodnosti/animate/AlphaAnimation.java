package sk.zawy.lahodnosti.animate;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;

public class AlphaAnimation {

    public void toAlphaAnimation(View view,float start, float end, int duration) {

            Animation animation = new android.view.animation.AlphaAnimation(start, end);
            animation.setDuration(duration);
            view.startAnimation(animation);

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setAlpha(end);
                }
            }, duration);

        }
    }




