package top.bilibililike.player.widget.search;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import top.bilibililike.player.R;
import top.bilibililike.player.common.bean.search.SearchResultBean;

public class SearchAdapter0 extends BaseMultiItemQuickAdapter<SearchResultBean.DataBean.ItemBean, BaseViewHolder> implements LoadMoreModule {
    public SearchAdapter0() {
        addItemType(SearchResultBean.DataBean.ItemBean.AV, R.layout.item_search_video_recycler);
        addItemType(SearchResultBean.DataBean.ItemBean.BANGUMI, R.layout.item_search_bangumi_recycler);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, SearchResultBean.DataBean.ItemBean item) {
        switch (helper.getItemViewType()) {
            case SearchResultBean.DataBean.ItemBean.AV:
                String title = item.getTitle().replace("em class=\"keyword\"", "font color=\"#fb7299\"").replace("</em>", "</font>");
                helper.setText(R.id.tv_play, Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY))
                        .setText(R.id.tv_plays, item.getPlay())
                        .setText(R.id.tv_duration, item.getDuration()+"")
                        .setText(R.id.tv_comments, item.getDanmaku()+"")
                        .setText(R.id.tv_nickname,item.getAuthor());

                Glide.with(helper.itemView).load(item.getCover()).into((ImageView) helper.getView(R.id.imv_cover));
                break;

            case SearchResultBean.DataBean.ItemBean.BANGUMI:
                String title1 = item.getTitle().replace("em class=\"keyword\"", "font color=\"#fb7299\"").replace("</em>", "</font>");
                helper.setText(R.id.tv_play, Html.fromHtml(title1+""));
                Glide.with(helper.itemView).load(item.getCover()).into((ImageView) helper.getView(R.id.imv_cover));
                break;

        }
    }
}
