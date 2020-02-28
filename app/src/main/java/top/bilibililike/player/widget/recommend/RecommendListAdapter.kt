package top.bilibililike.player.widget.recommend

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.recommend.Item





/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/28
 *  Describe:
 */

class RecommendListAdapter : BaseQuickAdapter<Item, BaseViewHolder>(R.layout.item_main_recycler,null),
    LoadMoreModule {
    override fun convert(helper: BaseViewHolder, item: Item) {
        helper.setText(R.id.tv_title,item.title)
            .setText(R.id.tv_up_nickname,item.args.up_name)
            .setText(R.id.tv_plays,item.cover_left_text_1)
            .setText(R.id.tv_comments,item.cover_left_text_2)
            .setText(R.id.tv_length,item.cover_right_text)
            .setVisible(R.id.tv_plays,true)
            .setVisible(R.id.tv_comments,true)
            .setVisible(R.id.tv_length,true)
        Glide.with(helper.itemView).load(item.cover).into(helper.getView(R.id.coverImg) as ImageView)
    }



}