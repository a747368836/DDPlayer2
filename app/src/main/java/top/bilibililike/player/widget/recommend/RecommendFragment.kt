package top.bilibililike.player.widget.recommend

import android.content.Intent
import android.util.Log
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
        refreshLayout.takeIf { refreshLayout.isRefreshing }?.apply { isRefreshing = false }
        val dataArrayList = ArrayList(response.items)
        val resultDatas = ArrayList<Item>()
        //api 中存在 article_s special_s ad_s 对应专栏 web广告 app内广告
        dataArrayList.forEach { item -> resultDatas.takeIf { !item.card_goto.contains("_") }?.apply { add(item) } }
        resultDatas.forEach{ item -> adapter?.addData(item)}
        adapter?.loadMoreModule.takeIf { adapter?.loadMoreModule!!.isLoading }?.apply { loadMoreComplete() }
    }

    override fun refreshListSuccess(response: Data) {
        refreshLayout.takeIf { refreshLayout.isRefreshing }?.apply { isRefreshing = false }
        val resultDatas = ArrayList<Item>()
        val dataArrayList = ArrayList(response.items)
        dataArrayList.forEach { item -> resultDatas.takeIf { !item.card_goto.contains("_s") }?.apply { add(item) } }
        adapter?.loadMoreModule.takeIf { adapter?.loadMoreModule!!.isLoading }?.apply { loadMoreComplete() }
        adapter?.setNewData(resultDatas)
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
        adapter?.loadMoreModule?.setOnLoadMoreListener { mPresenter.loadRecommendList(false) }
        adapter?.isUseEmpty = true

        adapter?.animationEnable = true
        adapter?.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data.get(position) as Item
            val intent = Intent()
            if (item.goto.contains("av")) {
                intent.setClass(context!!, PlayerActivity::class.java)
                intent.putExtra(Const.INTENT_VIDEO_AV, item.param)
            } else if (item.goto.contains("live")) {
                //roomId
                intent.setClass(context!!, PlayerActivity::class.java)
                intent.putExtra(Const.INTENT_VIDEO_LIVE, item.param)
            } else if (item.goto.contains("article")) {
                //4791917这是啥
                intent.putExtra(Const.INTENT_VIDEO_ARTICLE, item.param)
                intent.setClass(context!!, PlayerActivity::class.java)
            } else if (item.goto.contains("bangumi")) {
                //ep
                intent.setClass(context!!, PlayerActivity::class.java)
                intent.putExtra(Const.INTENT_VIDEO_BANGUMI, item.param)
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