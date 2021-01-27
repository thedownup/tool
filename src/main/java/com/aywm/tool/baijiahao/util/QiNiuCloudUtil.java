package com.aywm.tool.baijiahao.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;
import java.util.UUID;

/**
 * @description: 七牛与工具类
 * @author: zjt
 * @date: 2020-01-11 16:42
 */
@Component
public class QiNiuCloudUtil {

    private static Configuration configuration = new Configuration(Region.huanan());
    private static String accessKey = "oNumpBgBhUsx7NqYwcQH0w2TboPOUZnUfu5Qi9JM";
    private static String secretKey = "5xmACsnZ9u4i4R2YyzGri2UJt9o5WgzwogG8v_it";

    //用免费的域名 30天要换一次
    private static String bucket = "baijiahao1";
    private static String domain = "http://qluk4ku5g.hn-bkt.clouddn.com/";

    /**
     * 功能: 代理端口
     */
    @Value("${localSocketPort}")
    private int socketPort;

    public String getToken(String name) {
        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = "";
        if (StringUtils.isNullOrEmpty(name)) {
            uploadToken = auth.uploadToken(bucket);
        } else {
            uploadToken = auth.uploadToken(bucket, name);
        }
        return uploadToken;
    }


    /**
     * 功能: 使用代理访问
     * 作者: zjt
     * 日期: 2020/1/11 17:38
     * 版本: 1.0
     */
    public String getImageUrl(String imgUrl, int width, int height) throws Exception {
        String body = HttpRequest.post(imgUrl)
                .setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", socketPort)))
                .execute().body();
        UploadManager uploadManager = new UploadManager(configuration);
        Response response = uploadManager.put(body, imgUrl, getToken(""));
        return domain + JSON.parseObject(body, Map.class).get("key")
                + String.format("?imageMogr2/auto-orient/thumbnail/%sx%s!/blur/1x0/quality/75|imageslim", width, height);
    }

    /**
     * 功能: 文件上传
     * 作者: zjt
     * 日期: 2020/1/13 23:22
     * 版本: 1.0
     */
    public String fileUpload(String localFilePath) throws Exception {
        UploadManager uploadManager = new UploadManager(configuration);
        Response response = uploadManager.put(localFilePath, UUID.randomUUID().toString(), getToken(""));
        return domain + JSON.parseObject(response.bodyString(), Map.class).get("key");
    }

    public static void main(String[] args) throws Exception {
        QiNiuCloudUtil qiNiuCloudUtil = new QiNiuCloudUtil();
        String upload = qiNiuCloudUtil.fileUpload("I:\\home\\admin\\logs\\nm\\admin_debug_2019-03-07_1.log");
        System.out.println("upload = " + upload);
    }

}