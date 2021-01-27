package com.aywm.tool.enums;

/**
 * @description: 币世界granularity参数
 * @author: zjt
 * @date: 2020-12-26 20:33
 */
public enum GranularityEnum {

    /**
     * 5分钟
     */
    M5(2),

    /**
     * 15分钟
     */
    M15(3),

    /**
     * 30分钟
     */
    M30(4),

    /**
     * 1小时
     */
    H1(5),

    /**
     * 4小时
     */
    H4(6);

    GranularityEnum(int granularity) {
        this.granularity = granularity;
    }

    public int getGranularity() {
        return granularity;
    }

    private final int granularity;


}
