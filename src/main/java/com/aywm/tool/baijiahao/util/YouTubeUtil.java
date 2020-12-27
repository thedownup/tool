package com.aywm.tool.baijiahao.util;

import cn.hutool.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @description:
 * @author: zjt
 * @date: 2020-12-25 23:53
 */
@Component
public class YouTubeUtil {

    public static final String key = "AIzaSyCk0xCyL53AXrbNPKQku5YUhSGWBhLt73M";

    /**
     * 功能: 代理端口
     */
    @Value("${localSocketPort}")
    private int socketPort;

    /**
     * 功能: 重youtube上获取视频链接
     * 作者: zjt
     * 日期: 2020/12/25 23:54
     * 版本: 1.0
     */
    public static void getVideoList(String id) {
        String url = String.format("https://www.googleapis.com/youtube/v3/search?key=%s&channelId=%s&part=snippet,id&order=date&maxResults=%s", key, id, 10);
        System.out.println("url = " + url);
        String body = HttpRequest.get(url)
                .setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10801)))
                .execute().body();
        System.out.println("body = " + body);
    }

    public static void main(String[] args) {
        getVideoList("UCNz2YbRPdGvyBq5qq7iqNwQ");
    }

}