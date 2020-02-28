package top.bilibililike.player.widget.live.liveFragment


import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_live.*
import top.bilibililike.player.R
import top.bilibililike.player.widget.live.subtitle.bean.RepoBean
import top.bilibililike.player.widget.live.subtitle.roominfo.RoomInfoAdapter
import top.bilibililike.player.widget.live.subtitle.roominfo.RoomRepo
import top.bilibililike.player.widget.live.subtitle.utils.ToastUtil
import top.bilibililike.mvp.mvp.MVPFragment


class LiveFragment : MVPFragment<LiveContract.ILivePresenter>(),
    RoomInfoAdapter.ItemClickedCallback,
    LiveContract.ILiveView {



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


    }

    override fun initData() {
        RoomRepo.getLivers(object : RoomRepo.LiverCallback {
            override fun onSuccess(liverList: List<RepoBean.DataBean>) {
                (recyclerView.adapter as RoomInfoAdapter).refreshData(liverList)
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


    override fun onItemClicked(roomId: String) {

        Log.d("点击了", "点击了  $roomId")
    }

    override fun getTitle(): String = "直播"



}