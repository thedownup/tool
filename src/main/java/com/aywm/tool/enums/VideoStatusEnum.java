package com.aywm.tool.enums;

/**
 * @description: 视频枚举
 * @author: jintengzhou
 * @date: 2021-01-27 10:14
 */
public enum VideoStatusEnum {

    /**
     * 未下载
     */
    NOT_DOWNLOADED(0),

    /**
     * 下载
     */
    DOWNLOADED(1),

    /**
     * 上传成功
     */
    UPLOAD_SUCCESS(2),
    ;

    private int videoStatus;

    VideoStatusEnum(int videoStatus) {
        this.videoStatus = videoStatus;
    }

    public void setVideoStatus(int videoStatus) {
        this.videoStatus = videoStatus;
    }

    public int getVideoStatus() {
        return videoStatus;
    }

}