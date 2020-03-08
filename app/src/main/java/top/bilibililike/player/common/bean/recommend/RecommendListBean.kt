package top.bilibililike.player.common.bean.recommend

data class RecommendListBean(
    val code: Int,
    val `data`: Data,
    val message: String,
    val ttl: Int
)

data class Data(
    val config: Config,
    val items: List<Item>
)

data class Config(
    val auto_refresh_time: Int,
    val autoplay_card: Int,
    val column: Int,
    val feed_clean_abtest: Int,
    val home_transfer_test: Int
)

data class Item(
    val args: Args,
    val can_play: Int,
    val card_goto: String,
    val card_type: String,
    val cover: String,
    val cover_left_icon_1: Int,
    val cover_left_icon_2: Int,
    val cover_left_text_1: String,
    val cover_left_text_2: String,
    val cover_right_text: String,
    val desc_button: DescButton,
    val goto: String,
    val idx: Int,
    val official_icon: Int,
    val `param`: String,
    val player_args: PlayerArgs,
    val three_point: ThreePoint,
    val three_point_v2: List<ThreePointV2>,
    val title: String,
    val uri: String
)

data class Args(
    val rid: Int,
    val rname: String,
    val tid: Int,
    val tname: String,
    val up_id: Int,
    val up_name: String
)

data class DescButton(
    val event: String,
    val event_v2: String,
    val text: String,
    val type: Int,
    val uri: String
)

data class PlayerArgs(
    val aid: Int,
    val cid: Int,
    val type: String
)

data class ThreePoint(
    val dislike_reasons: List<DislikeReason>,
    val feedbacks: List<Feedback>,
    val watch_later: Int
)

data class DislikeReason(
    val id: Int,
    val name: String
)

data class Feedback(
    val id: Int,
    val name: String
)

data class ThreePointV2(
    val reasons: List<Reason>,
    val subtitle: String,
    val title: String,
    val type: String
)

data class Reason(
    val id: Int,
    val name: String
)