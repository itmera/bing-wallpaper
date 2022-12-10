package com.itmera.bing.html;

public class HtmlConstant {

    /**
     * 侧边栏目录的归档菜单
     */
    public static class Sidebar {
        public static final String VAR_SIDABAR = "${sidabar}";
        public static final String VAR_SIDABAR_NOW_COLOR = "sidabar_item sidabar_item_now";
        public static final String VAR_SIDABAR_COLOR = "sidabar_item";
        /**
         * <a href="#" onclick="w3_close()" class="w3-bar-item w3-button
         * w3-hover-text-green w3-large">2022-08</a>
         */

        // <p class="bg-gray-200 border rounded-full px-4 hover:bg-green-400">
        // 2022-01
        // </p>
        private static final String SIDABAR_MENU = "        <p class=\"sidabar_item\"><a href=\"${sidabar_href_url}\">${sidabar_href_title}</a></p>\n";

        public static String getSidabarMenuList(String hrefUrl, String hrefTitle) {
            String result = SIDABAR_MENU.replace("${sidabar_href_url}", hrefUrl);
            return result.replace("${sidabar_href_title}", hrefTitle);
        }
    }

    /**
     * 头部图片
     */
    public static class Head {
        public static final String HEAD_IMG_URL = "${head_img_url}";
        public static final String HEAD_IMG_DESC = "${head_img_desc}";
        public static final String HEAD_TITLE = "${head_title}";
    }

    /**
     * 图片列表
     */
    public static class ImgCard {
        public static final String VAR_IMG_CARD_LIST = "${img_card_list}";
        private static final String VAR_IMG_CARD_URL = "${img_card_url}";
        private static final String VAR_IMG_CARD_DATE = "${img_card_date}";
        private static final String IMG_CARD = ""
                + "        <div class=\"img_item\">\n"
                + "          <img src=\"${img_card_url}&pid=hp&w=384&h=216&rs=1&c=4\"/>\n"
                + "          <div>\n"
                + "            <p>${img_card_date}</p>\n"
                + "            <p> <a href=\"${img_card_url}\"  target=\"_blank\">Download 4k</a> </p>\n"
                + "          </div>\n        </div>\n";

        public static String getImgCard(String imgUrl, String date) {
            String result = IMG_CARD.replace(VAR_IMG_CARD_URL, imgUrl);
            return result.replace(VAR_IMG_CARD_DATE, date);
        }
    }

    /**
     * 底部归档
     */
    public static class MonthHistory {
        public static final String VAR_MONTH_HISTORY = "${month_history}";

        public static final String VAR_MONTH_HISTORY_NOW_MONTH_COLOR = "month_item month_now";
        public static final String VAR_MONTH_HISTORY_MONTH_COLOR = "month_item";
        private static final String VAR_MONTH_HISTORY_HREF_URL = "${month_href_url}";
        private static final String VAR_MONTH_HISTORY_HREF_TITLE = "${month_href_title}";

        // <p class="bg-gray-300 h-10 leading-10 text-lg text-center rounded">
        // <a href="">2022-11</a>
        // </p>
        private static final String MONTH_HISTORY_HREF = "        <p class=\"month_item\"><a href=\"${month_href_url}\">${month_href_title}</a></p>\n";

        public static String getMonthHistory(String url, String title) {
            String result = MONTH_HISTORY_HREF.replace(VAR_MONTH_HISTORY_HREF_URL, url);
            return result.replace(VAR_MONTH_HISTORY_HREF_TITLE, title);
        }
    }
}
