package com.aywm.tool.exchange.biword;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.aywm.tool.enums.GranularityEnum;
import com.aywm.tool.exchange.biword.bean.SkillTableInfo;
import com.aywm.util.MessageUtil;
import com.aywm.util.TypeCastUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description: 币世界技术启动器
 * @author: zjt
 * @date: 2020-12-26 19:58
 */
@Component
@Slf4j
public class BiWordBootStrap {

    public static volatile boolean startFlag = false;

    /**
     * 获取币种分析url
     */
    public static final String COIN_SKILL_TABLE_URL = "https://iapi.bishijie.com/skill/getCoinSkillTable";

    /**
     * 功能: 启动
     * 作者: zjt
     * 日期: 2020/12/26 19:59
     * 版本: 1.0
     */
    public static void start() {
        startFlag = true;
        log.info("启动 币世界监听程序");
    }

    /**
     * 功能: 获取监听的币种名称
     * 作者: zjt
     * 日期: 2020/12/26 20:01
     * 版本: 1.0
     */
    public static List<String> getListenerCoinNames() {
//        return Lists.newArrayList("bitcoin", "ethereum", "ripple", "litecoin",
//                "bitcoin-cash", "ethereum-classic", "eos", "bitcoin-cash-sv");
        return Lists.newArrayList("bitcoin", "bitcoin-cash-sv", "litecoin", "bitcoin-cash");
    }

    /**
     * 功能: 暂无描述
     * 作者: zjt
     * 日期: 2020/12/26 20:08
     * 版本: 1.0
     */
    public static Map<String, Object> getCoinSkillTable(String coinNam, GranularityEnum granularityEnum) {
        HttpResponse response = HttpUtil.createGet(COIN_SKILL_TABLE_URL + "?coin_id=" + coinNam + "&granularity=" + granularityEnum.getGranularity())
                .header("Origin", "https://multi.bishijie.com")
                .header("X-Requested-With", "com.bishijie")
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 9; COL-AL10 Build/HUAWEICOL-AL10; wv) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.136 Mobile Safari/537.36" +
                        " BSJApp uuid=f54eba10-75d4-3698-baab-79a7bb1d2fd6&sign=3aac538be663cca800549ddf6767d414&appVersion=351")
                .execute();

        String body = response.body();
        return JSON.parseObject(body, Map.class);
    }

    /**
     * 功能: 5分钟
     * 作者: zjt
     * 日期: 2020/12/26 21:09
     * 版本: 1.0
     */
    @Scheduled(cron = "0 0/5 * * * *")
    public void getCoinSkillTableInfo() {
        if (startFlag) {
            getListenerCoinNames().forEach(coinName -> {
                try {
                    Map<String, Object> coinSkillTableM5 = getCoinSkillTable(coinName, GranularityEnum.M5);
                    Map<String, Object> coinSkillTableM30 = getCoinSkillTable(coinName, GranularityEnum.M30);
                    SkillTableInfo skillTableInfoM5 = getInfoMap(coinSkillTableM5);
                    SkillTableInfo skillTableInfoM30 = getInfoMap(coinSkillTableM30);

                    log.info("监听{} skillTableInfoM5={}", skillTableInfoM5.getCoinPair(), skillTableInfoM5);

                    int threshold = 15;
                    //强烈买入
                    if (skillTableInfoM5.getPurchase() >= threshold && skillTableInfoM30.getPurchase() < threshold) {
                        MessageUtil.sendMessage(String.format("%s 出现买入信号 purchase=%s", skillTableInfoM5.getCoinPair(), skillTableInfoM5.getPurchase()), coinSkillTableM5);
                    }

                    //强烈卖出
                    if (skillTableInfoM5.getSellOut() >= threshold && skillTableInfoM30.getSellOut() < threshold) {
                        MessageUtil.sendMessage(String.format("%s 出现卖出信号 purchase=%s", skillTableInfoM5.getCoinPair(), skillTableInfoM5.getSellOut()), coinSkillTableM5);
                    }

                } catch (Exception e) {
                    log.error(String.format("getCoinSkillTableInfo失败 coinName=%s", coinName), e);
                }
            });
        }
    }

    /**
     * 功能: 获取infoMap
     * 作者: zjt
     * 日期: 2020/12/26 22:53
     * 版本: 1.0
     */
    private static SkillTableInfo getInfoMap(Map<String, Object> coinSkillTable) {
        SkillTableInfo skillTableInfo = new SkillTableInfo();
        Map<String, Object> data = (Map<String, Object>) coinSkillTable.get("data");
        Map<String, Object> infoMap = (Map<String, Object>) data.get("info");
        skillTableInfo.setCoinPair(TypeCastUtil.toString(infoMap.get("coin_pair")));
        skillTableInfo.setNeutral(TypeCastUtil.toInt(infoMap.get("neutral")));
        skillTableInfo.setPurchase(TypeCastUtil.toInt(infoMap.get("purchase")));
        skillTableInfo.setSellOut(TypeCastUtil.toInt(infoMap.get("sellOut")));
        return skillTableInfo;
    }

    public static void main(String[] args) {
        startFlag = true;
        new BiWordBootStrap().getCoinSkillTableInfo();
    }

}