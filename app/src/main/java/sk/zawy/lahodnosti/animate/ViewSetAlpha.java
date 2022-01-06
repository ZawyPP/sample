package sk.zawy.lahodnosti.animate;

import android.os.Handler;
import android.view.View;

public class ViewSetAlpha {
    private View widget;
    private int handlerDuration=0;

    public ViewSetAlpha(View widget, float start, float end, int duration ) {
        this.widget=widget;
        this.handlerDuration=duration;

        widget.setAlpha(start);
        widget.animate()
                .alpha(end)
                .setDuration(duration);
    }

    /** Po prvom Alpha prechode sa spustí s omeškaním (trvanie prvého prechodu) */
    public void after(float start, float end, int duration){
        Handler handler= new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                widget.setAlpha(start);
                widget.animate()
                        .alpha(end)
                        .setDuration(duration);
            }
        }, handlerDuration);
        }


}
