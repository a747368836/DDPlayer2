package top.bilibililike.player.widget.live.liveFragment


import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_live.*
import top.bilibililike.mvp.constant.Const
import top.bilibililike.player.R
import top.bilibililike.player.widget.live.subtitle.bean.RepoBean
import top.bilibililike.player.widget.live.subtitle.roominfo.RoomRepo
import top.bilibililike.player.widget.live.subtitle.utils.ToastUtil
import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.widget.live.subtitle.roominfo.RoomInfoAdapter
import top.bilibililike.player.widget.player.PlayerActivity


class LiveFragment : MVPFragment<LiveContract.ILivePresenter>(), LiveContract.ILiveView {

    var adapter: RoomInfoAdapter? = null;
    override fun getLayoutId(): Int {
        return R.layout.fragment_live
    }

    override fun bindPresenter(): LivePresenter {
        return LivePresenter()
    }

    override fun initView() {
        adapter =
            RoomInfoAdapter(R.layout.item_main_recycler)
        recyclerView.layoutManager = GridLayoutManager(context!!, 2)
        recyclerView.adapter = adapter
        adapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, PlayerActivity::class.java)
            intent.putExtra(
                Const.INTENT_VIDEO_LIVE,
                "${(adapter.data.get(position) as RepoBean.DataBean).room_info.room_id}"
            )
            startActivity(intent)
        }


    }

    override fun initData() {
        RoomRepo.getLivers(object : RoomRepo.LiverCallback {
            override fun onSuccess(liverList: List<RepoBean.DataBean>) {
                adapter?.setNewData(liverList.toMutableList())
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


    override fun getTitle(): String = "直播"


}