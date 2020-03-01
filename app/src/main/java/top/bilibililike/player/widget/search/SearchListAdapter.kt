package top.bilibililike.player.widget.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.search.SearchResultBean

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/3/1
 *  Describe:
 */

class SearchListAdapter : BaseQuickAdapter<SearchResultBean.DataBean.ItemBean, BaseViewHolder>(R.layout.item_search_recycler,null) {
    override fun convert(helper: BaseViewHolder, item: SearchResultBean.DataBean.ItemBean) {
        helper.setText(R.id.tv_title,item.title)
    }

}