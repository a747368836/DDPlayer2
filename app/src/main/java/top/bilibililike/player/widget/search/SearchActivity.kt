package top.bilibililike.player.widget.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_search.*
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.search.SearchResultBean
import top.bilibililike.player.supportClass.SmallLineDecoration


class SearchActivity : MVPActivity<SearchContract.Presenter>(), SearchContract.View {
    var adapter: SearchAdapter0? = null
    var page = 1;
    var keyword: String? = null
    override fun getSearchSuccess(searchBean: SearchResultBean.DataBean, isRefresh: Boolean) {
        val resultList = ArrayList<SearchResultBean.DataBean.ItemBean>()
        val item = searchBean.item
        item.forEach {
            it.takeIf { it.gotoX.contains("av") or it.gotoX.contains("bangumi") }
                ?.apply { resultList.add(it) }
        }
        if (isRefresh) {
            adapter?.setNewData(resultList)
            page = 1
        } else {
            resultList.forEach { adapter?.addData(it) }
            page++
        }


    }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun bindPresenter(): SearchContract.Presenter = SearchPresenter(this)

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter0()
        recycler_view.adapter = adapter
        txv_search.setOnClickListener {
            page = 1
            keyword = search_edtv.text.toString()
            presenter.getSearchResult(keyword!!, page, 20 * page)
        }
        adapter?.loadMoreModule?.setOnLoadMoreListener {
            presenter.getSearchResult(keyword!!, page, 20 * page)
        }

        recycler_view.addItemDecoration(SmallLineDecoration(1f))


    }

    override fun initData() {

    }


}