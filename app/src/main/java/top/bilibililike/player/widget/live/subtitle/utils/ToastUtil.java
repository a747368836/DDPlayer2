package top.bilibililike.player.widget.live.subtitle.utils;

import android.widget.Toast;

import top.bilibililike.player.common.MyApp;


/**
 * @author Xbs
 */
public class ToastUtil {
    public static void show(String str){
        Toast toast = Toast.makeText(MyApp.Companion.getMContext(),null,Toast.LENGTH_SHORT);
        toast.setText(str);
        toast.show();
    }
}
