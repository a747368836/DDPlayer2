package top.bilibililike.player.widget.videodetail.introduction

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.utilkit.FormatUtils

/**
 *   Created by xbs on 2020/3/8 15:14
 *   Description:
 */
class IntroductionRecommendListAdapter : BaseQuickAdapter<AvDescriptionBean.DataBean.RelatesBean, BaseViewHolder>
    (R.layout.item_search_video_recycler,null) {

    override fun convert(helper: BaseViewHolder, item: AvDescriptionBean.DataBean.RelatesBean) {
        Glide.with(helper.itemView)
            .load(item.pic)
            .into(helper.getView(R.id.imv_cover))
        helper.setText(R.id.tv_play,item.title)
            .setText(R.id.tv_nickname,item.owner.name)
            .setText(R.id.tv_plays,FormatUtils.getNum(item.stat.view))
            .setText(R.id.tv_comments,FormatUtils.getNum(item.stat.danmaku))
            .setText(R.id.tv_duration,FormatUtils.getDuration(item.duration))
    }
}