package com.aywm.tool;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-01-15 14:52
 */
public class YoutubeTest {

    public YouTube getYouTube() {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpRequestInitializer initializer = request -> request.setLoggingEnabled(true);
        NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
        builder.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10801)));
        YouTube.Builder youtubeBuilder = new YouTube.Builder(builder.build(), jsonFactory, initializer);
        youtubeBuilder.setApplicationName("aywm");
        youtubeBuilder.setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyCVYA0A-NjNvei8XdadkdVn_v4GYHi0Meo"));
        return youtubeBuilder.build();
    }

    @Test
    public void test1() throws IOException {
        YouTube youtube = getYouTube();

        YouTube.Search.List search = youtube.search().list("snippet");
        String apiKey = "AIzaSyCVYA0A-NjNvei8XdadkdVn_v4GYHi0Meo";
        search.setKey(apiKey);
        // 接口返回数据模型
        search.setType("video");
        // 设置需要接口返回的字段
//        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url),nextPageToken,pageInfo,prevPageToken");
        // 返回的最大记录条数
        search.setMaxResults(50L);
        // 设置要查询的channel id
        search.setChannelId("UCFwzOXPZKE6aH3fAU0d2Cyg");
        search.setOrder("date");

        final SearchListResponse searchListResponse = search.execute();
        final List<SearchResult> searchListResponseItems = searchListResponse.getItems();
        for (SearchResult searchListResponseItem : searchListResponseItems) {
            final ResourceId responseItemId = searchListResponseItem.getId();
            final String videoId = responseItemId.getVideoId();
            System.out.println("searchListResponseItem = " + searchListResponseItem.getSnippet());
        }


        System.out.println("searchListResponse = " + searchListResponse);
    }


    public void getVideoInfo(String playlistId) throws IOException {
        YouTube youtube = getYouTube();
        youtube.videos().list("snippet");
        final YouTube.PlaylistItems.List search = youtube.playlistItems().list("snippet");
        String apiKey = "AIzaSyCVYA0A-NjNvei8XdadkdVn_v4GYHi0Meo";
        search.setKey(apiKey);
        search.setMaxResults(50L);
    }

}