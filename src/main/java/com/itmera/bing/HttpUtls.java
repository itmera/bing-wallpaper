package com.itmera.bing;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtls {

    /**
     * 获取 HTTP 连接
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getHttpUrlConnection(String url) throws IOException {
        URL httpUrl = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) httpUrl.openConnection();
        httpConnection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
        httpConnection.setRequestProperty("accept-language",
                "zh-CN,zh;q=0.9");
        httpConnection.setRequestProperty("cookie",
                "SRCHHPGUSR=HV&SRCHLANG=zh-Hans;_EDGE_S=ui&SID=219F7D4B37126C181FC06F2436586DA7&mkt=zh-CN;");
        return httpConnection;
    }

    /**
     * 请求指定 URL 返回内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getHttpContent(String url) throws IOException {
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
        StringBuilder stringBuilder = new StringBuilder();
        // 获得输入流
        try (InputStream input = httpUrlConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(
                        input);) {
            byte[] buffer = new byte[1024];
            int len = -1;
            // 读到文件末尾则返回-1
            while ((len = bis.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpUrlConnection.disconnect();
        }
        return stringBuilder.toString();
    }

}
