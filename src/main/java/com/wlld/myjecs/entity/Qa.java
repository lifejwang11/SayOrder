package com.wlld.myjecs.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 聊天样本
 * @author laowang
 * @TableName q_a
 */
@Data
public class Qa implements Serializable {
    /**
     * 问题
     */
    private String question;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 回答
     */
    private String answer;

    private static final long serialVersionUID = 1L;
}