package com.aywm.tool.baijiahao.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @description: 百家号工具类
 * @author: zjt
 * @date: 2020-01-05 20:26
 */
@Slf4j
public class BaiJiaUtil {

    public static final String APP_ID = "1638947413591615";
    public static final String APP_TOKEN = "ef5a530bd602810862eab9876c214567";

    public static final String GET_ARTICLE_URL = "http://baijiahao.baidu.com/builderinner/open/resource/query/articleListall";
    private static final String WITH_DRAW_URL = "http://baijiahao.baidu.com/builderinner/open/resource/article/withdraw";
    public static final String VIDEO_PUBLISH_URL = "http://baijiahao.baidu.com/builderinner/open/resource/video/publish";
    public static final String VIDEO_STATUS_URL = "http://baijiahao.baidu.com/builderinner/open/resource/query/status";

    /**
     * 功能: 获取文章列表
     * 作者: zjt
     * 日期: 2020/1/5 20:48
     * 版本: 1.0
     */
    public static Map getArticle() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("app_token", APP_TOKEN);
        params.put("app_id", APP_ID);
        String res = HttpUtil.post(GET_ARTICLE_URL, params);
        return getMapRes(res);
    }


    /**
     * 功能: 发布视频
     * 作者: zjt
     * 日期: 2020/1/5 21:22
     * 版本: 1.0
     */
    public static Map<String, String> videoPublish(String title, String videoUrl, List<String> tags) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("app_token", APP_TOKEN);
        params.put("app_id", APP_ID);
        params.put("title", title);
        params.put("video_url", videoUrl);
        params.put("use_auto_cover", 1);
        if (!CollectionUtils.isEmpty(tags)) {
            StringBuilder tagstr = new StringBuilder();
            final int min = Math.min(tags.size(), 10);
            for (int i = 0; i < min; i++) {
                tagstr.append(tags.get(i));
                if (i + 1 != min) {
                    tagstr.append(",");
                }
            }
            params.put("tag", tagstr.toString());
        }
        String res = HttpUtil.post(VIDEO_PUBLISH_URL, params);
        return getMapRes(res);
    }

    /**
     * 功能: 发布视频 选择图片
     * 作者: zjt
     * 日期: 2020/1/11 16:25
     * 版本: 1.0
     */
    public static Map<String, String> videoPublish(String title, String imgurl, String videoUrl, List<String> tags) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("app_token", APP_TOKEN);
        params.put("app_id", APP_ID);
        params.put("title", title);
        params.put("video_url", videoUrl);
        params.put("cover_images", imgurl);
        if (!CollectionUtils.isEmpty(tags)) {
            StringBuilder tagstr = new StringBuilder();
            final int min = Math.min(tags.size(), 10);
            for (int i = 0; i < min; i++) {
                tagstr.append(tags.get(i));
                if (i + 1 != min) {
                    tagstr.append(",");
                }
            }
            params.put("tag", tagstr.toString());
        }
        String res = HttpUtil.post(VIDEO_PUBLISH_URL, params);
        return getMapRes(res);
    }

    /**
     * 功能: 获取Map类型返回值
     * 作者: zjt
     * 日期: 2020/12/24 21:44
     * 版本: 1.0
     */
    private static Map<String, String> getMapRes(String res) {
        return JSON.parseObject(res, Map.class);
    }

}