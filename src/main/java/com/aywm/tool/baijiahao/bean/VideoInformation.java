package com.aywm.tool.baijiahao.bean;

import com.google.api.services.youtube.model.VideoListResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 解析需要的视频
 * @author: zjt
 * @date: 2019-08-03 19:24
 */
@Document("videoInformation")
@Data
public class VideoInformation implements Serializable {
    @Id
    private String id;

    /**
     * 下载链接
     */
    private String downLoadurl;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 视频名称
     */
    private String title;

    /**
     * 原始名称
     */
    private String originTitle;

    /**
     * 下载状态
     */
    private Integer videoStatus;

    /**
     * 视频时长
     */
    private String time;

    /**
     * 保存的路径
     */
    private String savePath;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;

}