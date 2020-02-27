package top.bilibililike.player.ui.live.liveFragment

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder


import top.bilibililike.subtitle.subtitle.SubtitleService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_live.*
import top.bilibililike.player.R
import top.bilibililike.player.ui.live.subtitle.bean.RepoBean
import top.bilibililike.player.ui.live.subtitle.roominfo.RoomInfoAdapter
import top.bilibililike.player.ui.live.subtitle.roominfo.RoomRepo
import top.bilibililike.player.ui.live.subtitle.utils.ToastUtil
import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.mvp.widget.LoadingDialog
import top.bilibililike.mvp.widget.LoadingView


class LiveFragment : MVPFragment<LiveContract.ILivePresenter>(), RoomInfoAdapter.ClickCallback,
    LiveContract.ILiveView {


    private var subtitleService: SubtitleService? = null
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            subtitleService = (service as SubtitleService.LocalBinder).service
        }

        override fun onServiceDisconnected(arg0: ComponentName) {

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_live
    }

    override fun bindPresenter(): LivePresenter {
        return LivePresenter()
    }

    override fun initView() {
        val adapter = RoomInfoAdapter(this)
        recyclerView.layoutManager = GridLayoutManager(context!!, 2)
        recyclerView.adapter = adapter
        val intent = Intent(context, SubtitleService::class.java)
        context?.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        RoomRepo.getLivers(object : RoomRepo.LiverCallback {
            override fun onSuccess(liverList: List<RepoBean.DataBean>) {
                adapter.refreshData(liverList)
                hideLoading()
            }

            override fun onStartLoading() {
                showLoading()
            }

            override fun onError(reason: String) {
                ToastUtil.show(reason)
            }
        })
    }



    override fun onClicked(roomId: String) {

        Log.d("点击了","点击了  $roomId")
    }

    override fun getTitle():String = "直播"

    override fun onDestroy() {
        super.onDestroy()
        context?.unbindService(mConnection)
    }


}