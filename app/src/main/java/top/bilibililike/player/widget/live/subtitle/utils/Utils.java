package top.bilibililike.player.widget.live.subtitle.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static float dp2px(int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float sp2px(int sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,Resources.getSystem().getDisplayMetrics());
    }


}
