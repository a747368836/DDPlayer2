package top.bilibililike.player.widget.player.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import top.bilibililike.player.R
import kotlinx.android.synthetic.main.activity_live_player.*
import kotlinx.android.synthetic.main.activity_live_player.video_player
import kotlinx.android.synthetic.main.layout_player.*
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.bean.avUrl.Data
import top.bilibililike.player.common.bean.live.LivePlayUrlBean
import top.bilibililike.player.widget.player.LivePlayerContract
import top.bilibililike.player.widget.player.LivePlayerPresenter

class LivePlayerActivity : MVPActivity<LivePlayerContract.Presenter>(),
    LivePlayerContract.View {
    override fun bindPresenter(): LivePlayerContract.Presenter = LivePlayerPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_live_player

    override fun getVideoUrlSuccess(urlDataBean: Data) {

    }

    override fun getLiveUrlSuccess(liveUrlBean: LivePlayUrlBean.DataBean) {

    }

    override fun getVideoDetailSuccess(dataBean: AvDescriptionBean.DataBean) {
    }

    override fun initView() {
        val intent = intent
        val liveUrl = intent.getStringExtra("url")
        val headerMap = HashMap<String, String>()
        headerMap.put("Accept", " */*")
        headerMap.put("User-Agent", " Bilibili Freedoooooom/MarkII")
        video_player.mapHeadData = headerMap
        video_player.setUp(liveUrl,false,"直播测试")
        video_player.startAfterPrepared()

    }



}
