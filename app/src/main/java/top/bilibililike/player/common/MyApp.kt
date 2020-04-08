package top.bilibililike.player.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.model.VideoOptionModel
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import top.bilibililike.mvp.DDComponent

import top.bilibililike.player.common.apiConfig.MyRetrofitConfig
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.ConnectivityManager
import top.bilibililike.player.common.dao.userDataBase


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DDComponent.init(this, MyRetrofitConfig())
        mContext = applicationContext
        val tokenInfo = userDataBase.getTokenDao().getTokenAsyc()
        tokenInfo?.apply {
            hasLogin = true
            userToken = this.access_token
        }
        //GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        val videoOptionModel = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "User-Agent", "Bilibili Freedoooooom/MarkII")
        val list = ArrayList<VideoOptionModel>()

        val videoOptionModel0 = VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "Accept", "*/*")

        val videoOptionModel1 =
            VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max-buffer-size", 1024)
        val videoOptionModel2 =
            VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 3)
        val videoOptionModel3 =
            VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probsize", "4096")
        val videoOptionModel4 =
            VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", "2000000")
        val videoOptionModel5 =
            VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 50)
        list.add(videoOptionModel)
        list.add(videoOptionModel0)
        list.add(videoOptionModel1)
        list.add(videoOptionModel2)
        list.add(videoOptionModel3)
        list.add(videoOptionModel4)
        list.add(videoOptionModel5)
        GSYVideoManager.instance().optionModelList = list


    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
        var hasLogin = false;
        var userToken = "";

    }


}