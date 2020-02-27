package top.bilibililike.mvp.base;

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_list.*
import top.bilibililike.mvp.R
import top.bilibililike.mvp.mvp.BaseContract
import top.bilibililike.mvp.mvp.MVPActivity


abstract class ListActivity<P : BaseContract.Presenter, T> : MVPActivity<P>() {

    // 当前页
    var page = 0

    var refreshColor = R.color.refresh_color
        set(value) {
            field = value
            mSrlRefresh.setColorSchemeResources(refreshColor)
        }

    val adapter by lazy { bindAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_list

    override fun initView() {
        super.initView()

        // 初始化 SwipeRefreshLayout
        initRefresh()

        // 初始化 article
        initRecyclerView()
    }

    override fun initData() {
        super.initData()

        onRefreshData()
    }

    private fun initRefresh() {
        // 设置 下拉刷新 loading 颜色
        mSrlRefresh.setColorSchemeResources(refreshColor)
        mSrlRefresh.setOnRefreshListener {
            page = 0
            onRefreshData()
        }
    }

    private fun initRecyclerView() {

        mRvContent.layoutManager = LinearLayoutManager(this)
        mRvContent.adapter = adapter

        // 上拉加载更多
        adapter.setOnLoadMoreListener({
            ++page
            onLoadMoreData()
        }, mRvContent)

    }

    abstract fun bindAdapter(): BaseQuickAdapter<T, *>
    /**
     *  下拉刷新
     */
    open fun onRefreshData() {}

    /**
     *  上拉加载更多
     */
    open fun onLoadMoreData() {}

    fun addData(newData: List<T>) {

        // 如果为空的话，就直接 显示加载完毕
        if (newData.isEmpty()){
            adapter.loadMoreEnd()
            return
        }

        // 如果是 下拉刷新 直接设置数据
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            adapter.setNewData(newData)
            adapter.loadMoreComplete()
            return
        }

        // 否则 添加新数据
        adapter.addData(newData)
        adapter.loadMoreComplete()
    }
}
