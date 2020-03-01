package top.bilibililike.player.widget.search

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.search.SearchResultBean


class SearchActivity : MVPActivity<SearchContract.Presenter>(), SearchContract.View {
    var adapter:SearchListAdapter? = null

    override fun getSearchSuccess(searchBean: SearchResultBean.DataBean) {
        adapter?.setNewData(searchBean.item)
    }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun bindPresenter(): SearchContract.Presenter = SearchPresenter(this)

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = SearchListAdapter()
        recycler_view.adapter = adapter
        txv_search.setOnClickListener{
            presenter.getSearchResult(txv_search.text.toString())
        }


    }

    override fun initData() {

    }


}