package sk.zawy.lahodnosti.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import sk.zawy.lahodnosti.R;

public class DrawableRadius {
    private static final String LINEAR="linear";
    private static final String RELATIVE="relative";

    private Context context;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private final float radius;
    private ShapeAppearanceModel shapeAppearanceModel;
    private String what;

    public DrawableRadius(Context context, LinearLayout linearLayout, float radius) {
        this.context = context;
        this.radius = radius;
        this.linearLayout = linearLayout;
        what=LINEAR;
    }

    public DrawableRadius(Context context, RelativeLayout relativeLayout, float radius) {
        this.context = context;
        this.radius = radius;
        this.relativeLayout = relativeLayout;
        what=RELATIVE;
    }

    public void setBackgroundAndRadius( @ColorRes int color,float topleft,float topRight, float bottomLeft, float bottomRight){
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED,topleft)
                .setTopRightCorner(CornerFamily.ROUNDED,topRight)
                .setBottomRightCorner(CornerFamily.ROUNDED,bottomRight)
                .setBottomLeftCorner(CornerFamily.ROUNDED,bottomLeft)
                .build();


        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
//                new int[] {-android.R.attr.state_enabled}, // disabled
//                new int[] {-android.R.attr.state_checked}, // unchecked
//                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {
                Color.BLACK,
//                Color.RED,
//                Color.GREEN,
//                Color.BLUE
        };


        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        //Fill the LinearLayout with your color
        shapeDrawable.setFillColor(ContextCompat.getColorStateList(context, color));
        //Stroke color and width
        shapeDrawable.setStrokeWidth(4.0f);
        shapeDrawable.setStrokeColor(new ColorStateList(states,colors));

        switch (what){
            case LINEAR:
                ViewCompat.setBackground(linearLayout,shapeDrawable);
                break;
            case RELATIVE:
                ViewCompat.setBackground(relativeLayout,shapeDrawable);
                break;
        }

    }


}
