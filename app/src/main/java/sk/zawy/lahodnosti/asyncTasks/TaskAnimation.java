package sk.zawy.lahodnosti.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.animate.AlphaAnimation;

public class TaskAnimation extends AsyncTask <Void,Integer,Void>{

    private Context context;
    private ImageView imageView;
    private TextView textView;

    public TaskAnimation(Context context, ImageView imageView, TextView textView) {
        this.context=context;
        this.imageView=imageView;
        this.textView=textView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
    return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }


    @Override
    protected void onPostExecute(Void aVoid) {

        Random random=new Random();

        getImage(random.nextInt(7));

        List<String> titleList=new ArrayList<>();
        titleList.add(" LAHODNE ");
        titleList.add(" ZDRAVO ");
        titleList.add(" SLOW FOOD ");
        titleList.add(" KULTÚRA ");
        titleList.add(" DIVADLO ");
        titleList.add(" KONCERTY ");
        titleList.add(" WORKSHOPY ");
        titleList.add(" UDRŽATEĽNOSŤ ");
        titleList.add(" LOKÁLNOSŤ ");

        new CountDownTimer((15) * 300, 260){
            int tick = 0;
            @Override
            public final void onTick(final long millisUntilFinished){
                if(tick<titleList.size()) {
                    textView.setText(titleList.get(tick));
                }
               if(tick==6){
                   new AlphaAnimation().toAlphaAnimation(imageView,0.8f,0.5f,3000);
               }
                if(tick==10){
                    new AlphaAnimation().toAlphaAnimation(textView,0.8f,0.0f,800);
                }
                tick += 1;
            }
            @Override
            public void onFinish(){
            }

        }.start();
        super.onPostExecute(aVoid);
    }




    private void getImage(int values) {

        switch (values){
            case 0:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash01));
                break;
            case 1:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash06));
                break;
            case 2:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash03));
                break;
            case 3:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash04));
                break;
            case 4:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash05));
                break;
            case 5:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash07));
                break;
            case 6:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash08));
                break;
            case 7:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash09));
                break;
            case 8:
                imageView.setImageDrawable(context.getDrawable(R.drawable.splash02));
                break;
        }


    }

}
