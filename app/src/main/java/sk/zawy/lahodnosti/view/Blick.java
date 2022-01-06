package sk.zawy.lahodnosti.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import sk.zawy.lahodnosti.R;

public class Blick {

        public void startBlick(Context context, LinearLayout whatWillBlicking){
            ObjectAnimator animator= ObjectAnimator.ofInt(whatWillBlicking,"backgroundColor",
                    ContextCompat.getColor(context,R.color.orderTodayBlick),
                    ContextCompat.getColor(context, R.color.orderToday),
                    ContextCompat.getColor(context,R.color.orderTodayBlick),
                    ContextCompat.getColor(context,R.color.orderTodayBlick),
                    ContextCompat.getColor(context,R.color.orderToday),
                    ContextCompat.getColor(context,R.color.orderTodayBlick), android.R.color.transparent);
            animator.setDuration(3500);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setRepeatCount(0);
            animator.start();



        }





}
