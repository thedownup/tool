package com.aywm.tool.baijiahao.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @description: 百家号工具类
 * @author: zjt
 * @date: 2020-01-05 20:26
 */
@Slf4j
public class BaiJiaUtil {

    public static final String app_id = "1638947413591615";
    public static final String app_token = "ef5a530bd602810862eab9876c214567";

    public static final String getArticleUrl = "http://baijiahao.baidu.com/builderinner/open/resource/query/articleListall";
    private static final String withDrawUrl = "http://baijiahao.baidu.com/builderinner/open/resource/article/withdraw";
    public static final String videoPublishUrl = "http://baijiahao.baidu.com/builderinner/open/resource/video/publish";
    public static final String videoStatusUrl = "http://baijiahao.baidu.com/builderinner/open/resource/query/status";

    /**
     * 功能: 获取文章列表
     * 作者: zjt
     * 日期: 2020/1/5 20:48
     * 版本: 1.0
     */
    public static Map getArticle() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("app_token", app_token);
        params.put("app_id", app_id);
        String res = HttpUtil.post(getArticleUrl, params);
        return getMapRes(res);
    }


    /**
     * 功能: 发布视频
     * 作者: zjt
     * 日期: 2020/1/5 21:22
     * 版本: 1.0
     */
    public static Map<String, String> videoPublish(String title, String videoUrl) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("app_token", app_token);
        params.put("app_id", app_id);
        params.put("title", title);
        params.put("video_url", videoUrl);
        params.put("use_auto_cover", 1);
        String res = HttpUtil.post(videoPublishUrl, params);
        return getMapRes(res);
    }

    /**
     * 功能: 发布视频 选择图片
     * 作者: zjt
     * 日期: 2020/1/11 16:25
     * 版本: 1.0
     */
    public static Map<String, String> videoPublish(String title, String imgurl, String videoUrl) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("app_token", app_token);
        params.put("app_id", app_id);
        params.put("title", title);
        params.put("video_url", videoUrl);
        params.put("cover_images", imgurl);
        String res = HttpUtil.post(videoPublishUrl, params);
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