package top.bilibililike.player.common;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Type;

public class Utils {
    public static float dp2px(int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float sp2px(int sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,Resources.getSystem().getDisplayMetrics());
    }

    public static String dealNumber(int number){
        if (number > 10000) return number/10000 + "万";

        return number+"";
    }


}
