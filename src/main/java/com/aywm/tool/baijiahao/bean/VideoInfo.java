package com.aywm.tool.baijiahao.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 解析需要的视频
 * @author: zjt
 * @date: 2019-08-03 19:24
 */
@Document("videoinfo")
@Data
public class VideoInfo implements Serializable {
    @Id
    private String id;
    /**
     * 下载链接
     */
    private String url;
    /**
     * 视频名称
     */
    private String title;
    /**
     * 原始名称
     */
    private String originTitle;
    /**
     * 平台
     */
    private String platform;
    /**
     * 下载状态
     */
    private String status;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;
    /**
     * 视频时长
     */
    private String time;
    /**
     * 视频分辨率
     */
    private String resolution;
    /**
     * 保存的路径
     */
    private String savePath;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 视频类型
     */
    private String suffix;
    /**
     * 上传youtube的视频路径
     */
    private String youtubeUrl;
    /**
     * 本地保存路径
     */
    private String localSavePath;
    /**
     * 上传头条的flag
     */
    private String toutiaoFlag;
}