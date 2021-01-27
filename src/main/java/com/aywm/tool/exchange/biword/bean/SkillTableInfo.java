package com.aywm.tool.exchange.biword.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: zjt
 * @date: 2020-12-27 0:03
 */
@Data
public class SkillTableInfo implements Serializable {

    /**
     * 币名称
     */
    private String coinPair;

    /**
     * 卖出
     */
    private Integer sellOut;

    /**
     * 中立
     */
    private Integer neutral;

    /**
     * 买入
     */
    private Integer purchase;

}