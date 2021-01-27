package com.aywm.tool.controller;

import cn.hutool.core.util.ReUtil;
import com.aywm.tool.baijiahao.bean.VideoInformation;
import com.aywm.tool.baijiahao.util.YouTubeUtil;
import com.aywm.tool.enums.VideoStatusEnum;
import com.aywm.util.MessageUtil;
import com.aywm.util.TypeCastUtil;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-01-27 15:35
 */
@Slf4j
@RestController
@RequestMapping("wxPusher")
public class WxpusherController {

    @Autowired
    private YouTubeUtil youTubeUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 功能: 上行消息（用户发送消息回调）
     * 作者: zjt
     * 日期: 2021/1/27 15:44
     * 版本: 1.0
     */
    @RequestMapping("receiveMessage")
    public void receiveMessage(@RequestBody Map<String, Object> receiveMessageMap) {
        final Map<String, String> data = (Map<String, String>) receiveMessageMap.get("data");
        log.info("接收到用户发送消息回调={}", receiveMessageMap);
        final String content = data.get("content");
        final List<String> videoIdList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(content);
        if (!videoIdList.isEmpty()) {
            MessageUtil.sendMessage(String.format(String.format("处理 videoIdList=%s", videoIdList)));
            for (String videoId : videoIdList) {
                try {
                    final VideoListResponse videoListResponse = youTubeUtil.getVideoInfo(videoId);
                    final Video video = videoListResponse.getItems().get(0);
                    final VideoInformation videoInformation = new VideoInformation();
                    videoInformation.setCreateTime(new Date());
                    final VideoSnippet snippet = video.getSnippet();
                    videoInformation.setOriginTitle(snippet.getTitle());
                    videoInformation.setVideoStatus(VideoStatusEnum.NOT_DOWNLOADED.getVideoStatus());
                    videoInformation.setDownLoadurl("https://www.youtube.com/watch?v=" + video.getId());

                    final String durationOrigin = video.getContentDetails().getDuration();
                    final String duration = durationOrigin.toLowerCase().replaceAll("pt", "");
                    if (duration.contains("h")) {
                        MessageUtil.sendMessage(String.format("视频太大放弃 videoId=%s duration=%s", video, duration));
                        continue;
                    } else {
                        final String minute = ReUtil.get("(?<=PT).*?(?=M)", durationOrigin, 0);
                        if (TypeCastUtil.toInt(minute) > 30) {
                            MessageUtil.sendMessage(String.format("视频太大放弃 videoId=%s duration=%s", video, duration));
                            continue;
                        }
                    }

                    videoInformation.setTime(duration);
                    videoInformation.setThumbnail(snippet.getThumbnails().getDefault().getUrl());
                    videoInformation.setTags(snippet.getTags());
                    mongoTemplate.insert(videoInformation);
                } catch (Exception e) {
                    log.error(String.format("获取该视频videoId=%s信息失败", videoId), e);
                    MessageUtil.sendMessage(String.format("获取该视频videoId=%s信息失败 e=%s", videoId, e.getMessage()));
                }
            }
        }
    }

}