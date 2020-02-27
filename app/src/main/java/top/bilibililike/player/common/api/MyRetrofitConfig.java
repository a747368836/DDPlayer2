package top.bilibililike.player.common.api;


import org.jetbrains.annotations.NotNull;

import top.bilibililike.mvp.http.BaseRetrofitConfig;

public class MyRetrofitConfig extends BaseRetrofitConfig {
    @NotNull
    @Override
    public String getBaseUrl() {
        return Api.API_HOST;
    }
}
