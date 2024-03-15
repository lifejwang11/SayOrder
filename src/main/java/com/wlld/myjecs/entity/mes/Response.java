package com.wlld.myjecs.entity.mes;

import com.wlld.myjecs.config.ErrorCode;
import com.wlld.myjecs.entity.Admin;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.MyTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@Getter
@Setter
@ApiModel
public class Response<T> {
    private T data;
    @ApiModelProperty(value = "错误码", example = "1")
    private int error;
    @ApiModelProperty(value = "错误信息", example = "通讯异常")
    private String errorMessage;
    @ApiModelProperty(value = "语义识别结果")
    private Shop shop;
    @ApiModelProperty(value = "身份1是管理员 2是打标员")
    private int role;
    @ApiModelProperty(value = "返回的请求类型", example = "1")
    private int responseType;
    @ApiModelProperty(value = "当前所有分类")
    private List<MyTree> myTrees;
    @ApiModelProperty(value = "当前所有关键词")
    private List<KeywordType> keywordTypeList;
    @ApiModelProperty(value = "当前所有待通过账号")
    private List<Admin> admins;
    @ApiModelProperty(value = "返回id", example = "2")
    private int resultID;
    @ApiModelProperty(value = "返回今日标注语句")
    private List<AdminSentence> adminSentenceList;
    @ApiModelProperty(value = "回复语句")
    private String answer;

    public static <T> Response<T> ok(String errorMessage, T data) {
        Response<T> res = new Response<>();
        res.error = ErrorCode.OK.getError();
        res.errorMessage = errorMessage;
        res.data = data;
        return res;
    }

    public static <T> Response<T> ok(T data) {
        return ok(null, data);
    }

    public static <T> Response<T> fail(int error, String errorMessage, T data) {
        Response<T> res = new Response<>();
        res.error = error;
        res.errorMessage = errorMessage;
        res.data = data;
        return res;
    }

    public static <T> Response<T> fail(int error, String errorMessage) {
        return fail(error, errorMessage, null);
    }
}
