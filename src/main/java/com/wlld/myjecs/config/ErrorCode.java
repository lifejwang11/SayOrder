package com.wlld.myjecs.config;

import lombok.Getter;

@Getter
public enum ErrorCode {
    OK("正常", 0),
    WordIsNull("说话语句长度小于2", 1),
    notStartModel("当前服务是测试模式，该接口未开启，请将服务调整为生产模式后重启服务", 2),
    invalidAccount("该账号重复或无效", 4),
    NotPower("您的账号或者密码无效", 5),
    invalidAdminID("操作的该用户不存在", 6),
    invalidTypeID("无效的类别ID或者该关键词并不存在于语句当中", 7),
    invalidSentence("该语句已经被添加过了", 8),
    sentenceNubTooMuch("此条目相关样本数量已经超过安全阈值，若要删除请修改安全阈值", 9),
    InvalidLogin("请先登录", 401),
    ModelNotStart("模型还没有准备好，它正在学习或者是还没有完成初始化", 11);
    private final String errorMessage;
    private final int error;

    ErrorCode(String errorMessage, int error) {
        this.errorMessage = errorMessage;
        this.error = error;
    }

}
