package com.aywm.tool.baijiahao.task;

import com.aywm.tool.baijiahao.bean.VideoInformation;
import com.aywm.tool.baijiahao.util.BaiJiaUtil;
import com.aywm.tool.baijiahao.util.QiNiuCloudUtil;
import com.aywm.tool.enums.VideoStatusEnum;
import com.aywm.tool.othersoft.sapher.youtubedl.YoutubeDL;
import com.aywm.tool.othersoft.sapher.youtubedl.YoutubeDLRequest;
import com.aywm.tool.othersoft.sapher.youtubedl.YoutubeDLResponse;
import com.aywm.util.Date8Util;
import com.aywm.util.GoogleTranslateUtil;
import com.aywm.util.TypeCastUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @description:
 * @author: zjt
 * @date: 2020-12-24 23:33
 */
@Component
@EnableScheduling
@Slf4j
public class BaiJiaHaoTask {

    @Autowired
    private QiNiuCloudUtil qiNiuCloudUtil;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    public static Date canPulishDate = null;

    /**
     * 功能: 下载视频
     * 作者: zjt
     * 日期: 2020/12/24 23:35
     * 版本: 1.0
     */
    @Scheduled(fixedDelay = 2000 * 60 * 5)
    public void downLoadVideo() {
        final Query searchQuery = new Query().addCriteria(Criteria.where("videoStatus").is(VideoStatusEnum.NOT_DOWNLOADED.getVideoStatus()));
        final List<VideoInformation> videoInformationList = mongoOperations.find(searchQuery, VideoInformation.class);
        log.info("开始下载视频 videoInformationList={}", videoInformationList);
        for (VideoInformation videoInformation : videoInformationList) {
            try {
                final String downLoadurl = videoInformation.getDownLoadurl();
                String fileName = UUID.randomUUID().toString();
                String fileDir = "/usr/local/tool/video";

                YoutubeDLRequest request = new YoutubeDLRequest(downLoadurl, fileDir);
                request.setOption("write-auto-sub");
                request.setOption("embed-subs");
                request.setOption("output", fileName);
                YoutubeDLResponse response = YoutubeDL.execute(request, (progress, etaInSeconds) -> log.info("下载该视频进度 videoInformation={} " + progress + "%", videoInformation.getOriginTitle()));

                String stdOut = response.getOut();
                log.info("下载视频返回值 val={}", stdOut);

                final File dir = new File(fileDir);
                for (File file : dir.listFiles()) {
                    if (file.getName().contains(fileName)) {
                        Update update = new Update();
                        update.set("videoStatus", VideoStatusEnum.DOWNLOADED.getVideoStatus());
                        update.set("savePath", file.getAbsolutePath());
                        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(videoInformation.getId())), update, VideoInformation.class);
                        log.info("下载视频成功 videoInformation={}", videoInformation);
                    }
                }

            } catch (Exception e) {
                log.error(String.format("下载该视频失败 videoInformation=%s", videoInformation), e);
            }

        }
    }


    /**
     * 功能: 上传视频
     * 作者: zjt
     * 日期: 2020/12/24 23:35
     * 版本: 1.0
     */
    @Scheduled(fixedDelay = 3000 * 60 * 5)
    public void uploadVideo() {

        if (canPulishDate != null && Date8Util.isSameDay(canPulishDate, new Date())) {
            return;
        }

        final Query searchQuery = new Query().addCriteria(Criteria.where("videoStatus").is(VideoStatusEnum.DOWNLOADED.getVideoStatus()));
        final List<VideoInformation> videoInformationList = mongoOperations.find(searchQuery, VideoInformation.class);
        log.info("获取要上传的视频 videoInformationList={}", videoInformationList);
        for (VideoInformation videoInformation : videoInformationList) {
            try {
                final String savePath = videoInformation.getSavePath();
                final String thumbnail = videoInformation.getThumbnail();
                //对标题进行翻译
                final String title = GoogleTranslateUtil.translateText(videoInformation.getOriginTitle(), "auto", "zh");
                log.info("要上传的视频翻译后的视频标题 title={}", title);
                final String downLoadUrl = qiNiuCloudUtil.fileUpload(savePath);
                Map<String, String> publishMap;

                if (StringUtils.isBlank(thumbnail)) {
                    publishMap = BaiJiaUtil.videoPublish(title, downLoadUrl, videoInformation.getTags());
                } else {
                    final String imageUrl = qiNiuCloudUtil.getImageUrl(thumbnail, 760, 470);
                    publishMap = BaiJiaUtil.videoPublish(title, imageUrl, downLoadUrl, videoInformation.getTags());
                }

                log.info("上传视频 publishMap={}", publishMap);

                final String errno = TypeCastUtil.toString(publishMap.get("errno"));

                //0代表成功
                if ("0".equals(errno)) {
                    Update update = new Update();
                    update.set("videoStatus", VideoStatusEnum.UPLOAD_SUCCESS.getVideoStatus());
                    mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(videoInformation.getId())), update, VideoInformation.class);
                    log.info("上传视频成功 videoInformation={}", videoInformation);
                    //删除文件
                    new File(savePath).delete();
                } else {
                    //发文篇数用尽
                    if ("60001009".equals(errno)) {
                        canPulishDate = new Date();
                    }
                    log.info("上传视频失败 原因={} videoInformation={}", publishMap, videoInformation);
                }
            } catch (Exception e) {
                log.error(String.format("上传视频失败 videoInformation=%s", videoInformation), e);
            }
        }
    }
}