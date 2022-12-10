package com.itmera.bing;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itmera.bing.html.WebSiteGenerator;

public class Wallpaper {

    // BING API
    private static String BING_API = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=10&nc=1612409408851&pid=hp&FORM=BEHPTB&uhd=1&uhdwidth=3840&uhdheight=2160";

    private static String BING_URL = "https://cn.bing.com";

    public static void main(String[] args) throws IOException {
        String httpContent = HttpUtls.getHttpContent(BING_API);
        JSONObject jsonObject = JSON.parseObject(httpContent);
        JSONArray jsonArray = jsonObject.getJSONArray("images");

        List<Images> bingList = BingFileUtils.readBing();
        Integer day = args.length >= 1 ? Integer.parseInt(args[0]) : 0;
        JSONObject imageContent = jsonArray.getJSONObject(day);

        // 图片地址
        String url = BING_URL + imageContent.getString("url");
        url = url.substring(0, url.indexOf("&"));

        System.out.println(imageContent.getString("fullstartdate"));
        // 图片时间
        String enddate = imageContent.getString("enddate");
        LocalDate localDate = LocalDate.parse(enddate, DateTimeFormatter.BASIC_ISO_DATE);
        enddate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        // 图片版权
        final String desc = String.format("%s %s", imageContent.getString("title"),
                imageContent.getString("copyright"));
        // 加入列表
        bingList.add(0, new Images(desc, enddate, url));

        bingList = bingList.stream().distinct().collect(Collectors.toList());
        // 根据日期进行排序
        Collections.sort(bingList, new Comparator<Images>() {
            @Override
            public int compare(Images o1, Images o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        BingFileUtils.writeBing(bingList);
        BingFileUtils.writeBingJson(bingList);
        BingFileUtils.writeReadme(bingList);
        BingFileUtils.writeMonthInfo(bingList);
        new WebSiteGenerator().htmlGenerator();
    }

}
