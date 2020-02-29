package top.bilibililike.player.common.utilkit;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
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

    static float uk=0,uk1=0,duk=0;	//pid输出值
    static float Kp=5,Ki=1.5f,Kd=0.9f;	//pid控制系数
    static int e=0,e1=0,e2=0;			//pid 偏差
    static float out = 0; //输出
    public static float getPIDSpeed(long currentTime,long target){
        e = (int) (target - currentTime);//偏差
        duk=(Kp*(e-e1)+Ki*e)/100;//只调节PI
        uk=uk1+duk;//uk=u(k-1)+Δuk
        out = uk;
        if(out>250)	//设置最大限制
            out=250;
        else if(out<0)//设置最小限制
            out=0;
        uk1=uk;		  //为下一次增量做准备
        e2=e1;
        e1=e;
        Log.d("PlayActivity","speedPID = " + out);
        return out;

    }


}
