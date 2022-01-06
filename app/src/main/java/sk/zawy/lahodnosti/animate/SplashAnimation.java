package sk.zawy.lahodnosti.animate;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.activities.MainScreen;
import sk.zawy.lahodnosti.asyncTasks.TaskAnimation;
import sk.zawy.lahodnosti.view.TabLayout;

public class SplashAnimation {
    private Activity activity;
    private View viewActivity;
    private ImageView backgroundSplash1;
    private TextView title;

    public SplashAnimation(Activity mainActivity)  {
        this.activity=mainActivity;
        viewActivity=mainActivity.findViewById(android.R.id.content);

        this.title = (TextView) viewActivity.findViewById(R.id.titleSplash);
        this.backgroundSplash1 = (ImageView) viewActivity.findViewById(R.id.backgroundSplash1);

        title.setVisibility(View.VISIBLE);
        Handler handler=new Handler();

        try {

            new TaskAnimation(backgroundSplash1.getContext(), backgroundSplash1, title).execute();

        }catch (OutOfMemoryError e){
            /* ...fix in manifest (but fix make layout error-animate to alpha )
            android:hardwareAccelerated="false"
            android:largeHeap="true"
             */
            Log.d("logData","SplashAnimation" + e);
        }catch (Exception e){
            Log.d("logData","ERROR for SplashAnimation" + e);
            e.printStackTrace();
        }finally {
            handler.postDelayed(runnableEnd,4300);

        }
    }


    private Runnable runnableEnd=new Runnable() {
        @Override
        public void run() {
            TabLayout.position=0;
            Intent i=new Intent(activity, MainScreen.class);
            activity.startActivity(i);
            activity.finish();
        }
    };
}
