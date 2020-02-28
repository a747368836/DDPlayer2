package top.bilibililike.player.common.api;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class Api {
    public static final String DOMAIN_HEADER = "domain";
    public static final String BILI_HEADER = "bili";
    public static final String LOGIN_HOST = "https://passport.bilibili.com";
    public static final String APP_HOST = "https://app.bilibili.com";
    public static final String API_HOST = "https://api.bilibili.com";
    public static final String BILIPLUS_HOST = "https://www.biliplus.com";

    public static final String appsecret = "560c52ccd288fed045859ed18bffd973";
    public static final String videosecret = "aHRmhWMLkdeMuILqORnYZocwMBpMEOdt";

    public static final String videoAppkey = "iVGUTjsxvpLeuDCf";
    public static final String appkey = "1d8b6e7d45233436";
    public static final String build = "5410000";
    public static final String channel = "bili";
    public static final String mobi_app = "android";
    public static final String platform = "android";
    public static final String statistics = "{\"appId\":1,\"platform\":3,\"version\":\"5.41.0\",\"abtest\":\"\"}";
    private static ConcurrentHashMap<String,String> params ;
    public static void initParams(){
        /*TokenBean.DataBean.TokenInfoBean tokenBean = null  ;
        try{
            tokenBean = LitePal.find(TokenBean.DataBean.TokenInfoBean.class,1);
        }catch (Exception ignored){

        }*/


        if (params != null){
            params.clear();
        }else {
            params = new ConcurrentHashMap<String,String>();
        }

      /*  if (tokenBean != null && tokenBean.getAccess_token() != null){
            params.put("access_key",tokenBean.getAccess_token());
            Log.d("Api access_key",tokenBean.getAccess_token());
        }
*/

        params.put("appkey",appkey);
        params.put("build",build);
        params.put("channel",channel);
        params.put("mobi_app",mobi_app);
        params.put("platform",platform);
        params.put("statistics",statistics);
        params.put("ts", String.valueOf(System.currentTimeMillis()).substring(0,10));
    }
    public static ConcurrentHashMap<String,String> getParams(){
        initParams();
        return params;
    }

}
