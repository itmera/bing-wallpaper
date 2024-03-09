package com.zzlan.bingwallpaper.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {
    private static final java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
    public  String getData(String url) throws IOException, InterruptedException {
        // 创建 HTTP 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept","application/json")
                .header("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("cache-control", "no-cache")
                .header("cookie", "SRCHHPGUSR=HV&SRCHLANG=zh-Hans;_EDGE_S=ui&SID=219F7D4B37126C181FC06F2436586DA7&mkt=zh-CN;")
                .GET()
                .build();
        // 发送请求并获取响应
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(request.headers());
        return response.body();
    }
}
