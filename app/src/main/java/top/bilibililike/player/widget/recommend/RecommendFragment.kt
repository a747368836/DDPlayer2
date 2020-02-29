package top.bilibililike.player.widget.recommend

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_recommend.*
import top.bilibililike.mvp.constant.Const
import top.bilibililike.mvp.ext.Toasts.toast
import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.recommend.Data
import top.bilibililike.player.common.bean.recommend.Item
import top.bilibililike.player.widget.player.PlayerActivity


class RecommendFragment : MVPFragment<RecommendContract.Presenter>(), RecommendContract.View {
    override fun loadMoreListSuccess(response: Data) {
        val datas = response.items
        val dataArrayList = ArrayList(datas)
        val resultDatas = ArrayList<Item>()
        dataArrayList.forEach({
            item -> if (!item.card_goto.contains("ad") ) resultDatas.add(item)
        })
        for (data in resultDatas) {
            adapter?.addData(data)
        }
        adapter?.loadMoreModule?.loadMoreComplete()
    }

    override fun refreshListSuccess(response: Data) {
        loadRecommendListSuccess(response)
    }

    override fun loadRecommendListSuccess(response: Data) {
        refreshLayout.isRefreshing = false
        adapter?.setNewData(response.items as MutableList<Item>)
    }

    override fun showError(msg: String) {
        toast(msg)
    }



    var adapter: RecommendListAdapter? = null


    override fun getTitle(): String {
        return "推荐"
    }

    override fun getLayoutId(): Int = R.layout.fragment_recommend

    override fun bindPresenter(): RecommendContract.Presenter = RecommendPresenter(this)

    override fun initView() {
        adapter = RecommendListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter?.loadMoreModule?.isAutoLoadMore = true
        adapter?.loadMoreModule?.setOnLoadMoreListener { mPresenter.loadMoreList() }
        adapter?.isUseEmpty = true

        adapter?.animationEnable = true
        adapter?.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data.get(position) as Item
            val intent = Intent()
            if (item.goto.contains("av")){
                intent.setClass(context!!, PlayerActivity::class.java)
                intent.putExtra(Const.INTENT_VIDEO_AV,item.param)
            }
            else if (item.goto.contains("live")){
                //roomId
                intent.setClass(context!!, PlayerActivity::class.java)
                intent.putExtra(Const.INTENT_VIDEO_LIVE,item.param)
            }else if (item.goto.contains("article")){
                //4791917这是啥
                intent.putExtra(Const.INTENT_VIDEO_ARTICLE,item.param)
                intent.setClass(context!!, PlayerActivity::class.java)
            }else if (item.goto.contains("bangumi")){
                //ep
                intent.setClass(context!!, PlayerActivity::class.java)
                intent.putExtra(Const.INTENT_VIDEO_BANGUMI,item.param)
            }

            startActivity(intent)
        }
        refreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        refreshLayout.setOnRefreshListener { mPresenter.loadRecommendList(true) }
    }

    override fun initData() {
        refreshLayout.isRefreshing = true
        mPresenter.loadRecommendList(true)
    }
}