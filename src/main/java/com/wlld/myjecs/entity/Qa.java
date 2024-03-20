package com.wlld.myjecs.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 聊天样本
 * @author laowang
 * @TableName q_a
 */
@Data
@TableName("q_a")
public class Qa implements Serializable {
    /**
     * 问题
     */
    private String question;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 回答
     */
    private String answer;

    private static final long serialVersionUID = 1L;
}