package com.aywm.tool.baijiahao.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

/**
 * @description:
 * @author: zjt
 * @date: 2020-12-25 23:53
 */
@Component
public class YouTubeUtil {

    public static final String key = "AIzaSyCVYA0A-NjNvei8XdadkdVn_v4GYHi0Meo";

    /**
     * 功能: 代理端口
     */
    @Value("${localSocketPort}")
    private int socketPort;

    private YouTube getYouTube() {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpRequestInitializer initializer = request -> request.setLoggingEnabled(true);
        NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
        builder.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", socketPort)));
        YouTube.Builder youtubeBuilder = new YouTube.Builder(builder.build(), jsonFactory, initializer);
        youtubeBuilder.setApplicationName("aywm");
        youtubeBuilder.setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyCVYA0A-NjNvei8XdadkdVn_v4GYHi0Meo"));
        return youtubeBuilder.build();
    }

    /**
     * 功能: 重youtube上获取视频链接
     * 作者: zjt
     * 日期: 2020/12/25 23:54
     * 版本: 1.0
     */
    public static void getVideoList(String id, int pageSize) {
        String url = String.format("https://www.googleapis.com/youtube/v3/search?key=%s&channelId=%s&part=snippet,id&order=date&maxResults=%s", key, id, 10);
        System.out.println("url = " + url);
        String body = HttpRequest.get(url)
                .setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10801)))
                .execute().body();
        Map map = JSON.parseObject(body, Map.class);

    }

    public static void main(String[] args) {
        getVideoList("UCNz2YbRPdGvyBq5qq7iqNwQ", 1);
    }

}