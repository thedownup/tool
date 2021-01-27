package com.aywm.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjiecode.wxpusher.client.WxPusher;
import com.zjiecode.wxpusher.client.bean.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * @description: wxpusher推送到微信
 * @author: zjt
 * @date: 2020-12-26 22:02
 */
@Slf4j
public class MessageUtil {

    public static final String APP_TOKEN = "AT_Aav4yAPhwellmBkL3pLO9DOGJki2ex9L";
    public static final String MY_UID = "UID_0ysq16EvngWBf4SjP18sT70dKdmq";

    /**
     * 功能: 发送消息
     * 作者: zjt
     * 日期: 2020/12/26 22:03
     * 版本: 1.0
     */
    public static void sendMessage(String content) {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_HTML);
        message.setContent(content + " 发送时间:" + Date8Util.format(new Date(), Date8Util.DATE_TIME));
        message.setUid(MY_UID);
        WxPusher.send(message);
        log.info("发送消息:" + message.getContent());
    }

    /**
     * 功能: 发送消息
     * 作者: zjt
     * 日期: 2020/12/26 22:03
     * 版本: 1.0
     */
    public static void sendMessage(String Summary, Map map) {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setSummary(Summary);
        message.setContentType(Message.CONTENT_TYPE_MD);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String writeValueAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            message.setContent(writeValueAsString + "<br> 发送时间:" + Date8Util.format(new Date(), Date8Util.DATE_TIME));
        } catch (Exception e) {
            log.error(String.format("格式json失败 map=%s", map), e);
            message.setContent(JSON.toJSONString(map) + "<br> 发送时间:" + Date8Util.format(new Date(), Date8Util.DATE_TIME));
        }
        message.setUid(MY_UID);
        WxPusher.send(message);
        log.info("发送消息:" + message.getContent());
    }
}