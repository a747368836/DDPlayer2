package top.bilibililike.player.common.utilkit;

public class FormatUtils {
    /**
     *
     * @param duration 秒数 180
     * @return 小时-分钟  3:00
     */
    public static String getDuration(int duration){
        if (duration < 60){
            return String.valueOf(duration);
        }
        int minutes = duration/60 % 60;
        int seconds = duration % 60;
        if (duration < 60*60){
            return minutes+":"+seconds;
        }else {
            int hours = duration / 60 /60;
            return hours+":"+minutes+":"+seconds;
        }
    }

    /**
     *
     * @param num 12321
     * @return 1.23万
     */
    public static String getNum(int num){
        if (num < 9999){
            return String.valueOf(num);
        }
        StringBuilder resultBuilder = new StringBuilder(String.format("%.2f",num/10000.0));
        if (resultBuilder.lastIndexOf(".00") != -1) resultBuilder.delete(0,resultBuilder.length());
        resultBuilder.append("万");
        return resultBuilder.toString();
    }

    //todo 时间戳转日期 没记错的话 1583581920
    public static String getPublishTime(int stamp){

        return String.valueOf(stamp);
    }
}
