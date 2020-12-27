package com.aywm.tool.baijiahao.task;

import com.aywm.tool.baijiahao.util.QiNiuCloudUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

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

    /**
     * 功能: 下载视频
     * 作者: zjt
     * 日期: 2020/12/24 23:35
     * 版本: 1.0
     */
    public void downLoadVideo() {

    }


    /**
     * 功能: 上传视频
     * 作者: zjt
     * 日期: 2020/12/24 23:35
     * 版本: 1.0
     */
}