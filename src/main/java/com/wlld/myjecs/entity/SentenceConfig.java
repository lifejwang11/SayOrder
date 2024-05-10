package com.wlld.myjecs.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 训练配置表
 * @TableName sentence_config
 */
@TableName(value ="sentence_config")
@Data
public class SentenceConfig implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 语义分类种类数与表my_tree数据条数对应
     */
    @TableField(value = "type_nub")

    @NotNull(message = "请输入语义分类种类数")
    private Integer typeNub;

    /**
     * 语义分类词嵌入维度，该数字越大，支持的分类复杂度越高，支持数据量越多，越接近大模型，生成问答模型越稳定，但速度越慢
     */
    @TableField(value = "word_vector_dimension")

    @NotNull(message = "请输入语义分类词嵌入维度")
    private Integer wordVectorDimension;

    /**
     * 问答模型词嵌入维度，该数字越大速度越慢，越能支持更复杂的问答
     */
    @TableField(value = "qa_word_Vector_dimension")

    @NotNull(message = "请输入问答模型词嵌入维度")
    private Integer qaWordVectorDimension;

    /**
     * 用户输入语句最大长度
     */
    @TableField(value = "max_word_length")

    @NotNull(message = "请输入用户输入语句最大长度")
    private Integer maxWordLength;

    /**
     * Ai最大回答长度
     */
    @TableField(value = "max_answer_length")

    @NotNull(message = "请输入Ai最大回答长度")
    private Integer maxAnswerLength;

    /**
     * /关键词敏感嗅探颗粒度大小
     */
    @TableField(value = "key_word_nerve_deep")

    @NotNull(message = "请输入关键词敏感嗅探颗粒度大小")
    private Integer keyWordNerveDeep;

    /**
     * 训练次数
     */
    @TableField(value = "times")

    @NotNull(message = "请输入模型训练增强")
    private Integer times;

    /**
     * 是否显示训练日志 1是0否
     */
    @TableField(value = "show_log")
    private Integer showLog;

    /**
     *
     */
    @TableField(value = "param")
    @NotNull(message = "请输入正则抑制系数")
    private Double param;

    /**
     *
     */
    @TableField(value = "min_length")
    private Integer minLength;

    /**
     *
     */
    @TableField(value = "trust_power_th")
    @NotNull(message = "请输入语义分类可信阈值")
    private Double trustPowerTh;

    /**
     *
     */
    @TableField(value = "sentence_trust_power_th")
    @NotNull(message = "请输入生成语句可信阈值")
    private Double sentenceTrustPowerTh;

    /**
     *
     */
    @TableField(value = "we_study_point")
    private Double weStudyPoint;

    /**
     *
     */
    @TableField(value = "we_lparam")
    private Double weLparam;

    /**
     * 状态（1使用 0未使用）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time",fill = FieldFill.UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}