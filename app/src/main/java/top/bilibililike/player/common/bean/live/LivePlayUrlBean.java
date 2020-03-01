package top.bilibililike.player.common.bean.live;

import java.util.List;

public class LivePlayUrlBean {

    /**
     * code : 0
     * message : 0
     * ttl : 1
     * data : {"current_quality":4,"accept_quality":["4"],"current_qn":10000,"quality_description":[{"qn":10000,"desc":"原画"}],"durl":[{"url":"https://txy.live-play.acgvideo.com/live-txy/482793/live_412135222_36784435.flv?wsSecret=d7dbf85998fb8370b63b89c84dedeb05&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=1&sig=no","length":0,"order":1,"stream_type":0},{"url":"https://txy.live-play.acgvideo.com/live-txy/612961/live_412135222_36784435.flv?wsSecret=5473459c342548bbb97e095419468cc2&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=2&sig=no","length":0,"order":1,"stream_type":0},{"url":"https://txy.live-play.acgvideo.com/live-txy/848711/live_412135222_36784435.flv?wsSecret=e6d534e1b23fccc818764ce5b7a2e18c&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=3&sig=no","length":0,"order":1,"stream_type":0},{"url":"https://txy.live-play.acgvideo.com/live-txy/529169/live_412135222_36784435.flv?wsSecret=d79072ef353322e4fced079399bc31ba&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=4&sig=no","length":0,"order":1,"stream_type":0}]}
     */

    private int code;
    private String message;
    private int ttl;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * current_quality : 4
         * accept_quality : ["4"]
         * current_qn : 10000
         * quality_description : [{"qn":10000,"desc":"原画"}]
         * durl : [{"url":"https://txy.live-play.acgvideo.com/live-txy/482793/live_412135222_36784435.flv?wsSecret=d7dbf85998fb8370b63b89c84dedeb05&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=1&sig=no","length":0,"order":1,"stream_type":0},{"url":"https://txy.live-play.acgvideo.com/live-txy/612961/live_412135222_36784435.flv?wsSecret=5473459c342548bbb97e095419468cc2&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=2&sig=no","length":0,"order":1,"stream_type":0},{"url":"https://txy.live-play.acgvideo.com/live-txy/848711/live_412135222_36784435.flv?wsSecret=e6d534e1b23fccc818764ce5b7a2e18c&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=3&sig=no","length":0,"order":1,"stream_type":0},{"url":"https://txy.live-play.acgvideo.com/live-txy/529169/live_412135222_36784435.flv?wsSecret=d79072ef353322e4fced079399bc31ba&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=4&sig=no","length":0,"order":1,"stream_type":0}]
         */

        private int current_quality;
        private int current_qn;
        private List<String> accept_quality;
        private List<QualityDescriptionBean> quality_description;
        private List<DurlBean> durl;

        public int getCurrent_quality() {
            return current_quality;
        }

        public void setCurrent_quality(int current_quality) {
            this.current_quality = current_quality;
        }

        public int getCurrent_qn() {
            return current_qn;
        }

        public void setCurrent_qn(int current_qn) {
            this.current_qn = current_qn;
        }

        public List<String> getAccept_quality() {
            return accept_quality;
        }

        public void setAccept_quality(List<String> accept_quality) {
            this.accept_quality = accept_quality;
        }

        public List<QualityDescriptionBean> getQuality_description() {
            return quality_description;
        }

        public void setQuality_description(List<QualityDescriptionBean> quality_description) {
            this.quality_description = quality_description;
        }

        public List<DurlBean> getDurl() {
            return durl;
        }

        public void setDurl(List<DurlBean> durl) {
            this.durl = durl;
        }

        public static class QualityDescriptionBean {
            /**
             * qn : 10000
             * desc : 原画
             */

            private int qn;
            private String desc;

            public int getQn() {
                return qn;
            }

            public void setQn(int qn) {
                this.qn = qn;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class DurlBean {
            /**
             * url : https://txy.live-play.acgvideo.com/live-txy/482793/live_412135222_36784435.flv?wsSecret=d7dbf85998fb8370b63b89c84dedeb05&wsTime=1583053775&trid=fb50f72045ae4341aa095770f4a900fc&pt=android&oi=614315063&order=1&sig=no
             * length : 0
             * order : 1
             * stream_type : 0
             */

            private String url;
            private int length;
            private int order;
            private int stream_type;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getStream_type() {
                return stream_type;
            }

            public void setStream_type(int stream_type) {
                this.stream_type = stream_type;
            }
        }
    }
}
