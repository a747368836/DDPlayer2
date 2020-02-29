package top.bilibililike.player.widget.recommend

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_recommend.*
import top.bilibililike.mvp.ext.Toasts.toast
import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.recommend.Data
import top.bilibililike.player.common.bean.recommend.Item
import top.bilibililike.player.widget.player.playav.PlayAvActivity


class RecommendFragment : MVPFragment<RecommendContract.Presenter>(), RecommendContract.View {
    override fun loadMoreListSuccess(response: Data) {
        val datas = response.items
        for (data in datas) {
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
            val intent = Intent(activity,PlayAvActivity::class.java)
            intent.putExtra("PLAY_AV",item.param)
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