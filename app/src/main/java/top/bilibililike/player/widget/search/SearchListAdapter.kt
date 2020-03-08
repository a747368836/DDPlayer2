package top.bilibililike.player.widget.search

import android.text.Html
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.search.SearchResultBean

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/3/1
 *  Describe:
 */

class SearchListAdapter(data: MutableList<SearchResultBean.DataBean.ItemBean>?) :
    BaseMultiItemQuickAdapter<SearchResultBean.DataBean.ItemBean, BaseViewHolder>() {

    init {
        addItemType(SearchResultBean.DataBean.ItemBean.AV, R.layout.item_search_video_recycler)
        addItemType(SearchResultBean.DataBean.ItemBean.BANGUMI, R.layout.item_search_bangumi_recycler)
    }



    override fun convert(helper: BaseViewHolder, item: SearchResultBean.DataBean.ItemBean) {
        when(helper.itemViewType){
            SearchResultBean.DataBean.ItemBean.AV -> {
                //<em class="keyword">66号</em>  <font color="#FE6026">66</font>
                val start = "<em class=\"keyword\">"
                val end = "</em>"
                val title = item.title.replace(start,"<font color=\"#fb7299\"").replace(end,"</font>")
                helper.setText(R.id.tv_title,Html.fromHtml(title))
                    .setText(R.id.tv_plays,item.play)
                    ?.setText(R.id.tv_duration,item.duration)
                    ?.setText(R.id.tv_comments,item.danmaku)
                Glide.with(helper.itemView).load(item.cover).into(helper.getView(R.id.imv_cover) as ImageView)

            }
            SearchResultBean.DataBean.ItemBean.BANGUMI -> {
                takeIf { true }?.apply {  }
            }
        }
    }
}