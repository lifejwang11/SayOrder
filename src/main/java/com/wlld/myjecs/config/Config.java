package com.wlld.myjecs.config;

public class Config {//配置文件 "/usr/local/depends/
    public static final String Word2VecModelUrl = "D:\\testModel\\end.json";//词向量嵌入模型（注意词向量模型无需频繁更新，只有当样本有超过50%增量时才有更新必要）
    public static final String SentenceModelUrl = "D:\\testModel\\sentence.json";//语义分辨模型
    public static final String onlyKeyWord = "D:\\testModel\\preKeyWord.json";//关键词敏感性嗅探
    public static final String KeyWordModelUrl = "D:\\testModel\\myKeyWord.json";//关键词查找模型
    public static final String keyWordIndex = "D:\\testModel\\keyWordIndex.json";//关键词索引模型
    public static final boolean selfTest = false;//服务启动时是否需要自检（自检时间较长）
    public static final boolean starModel = true;//服务启动是否启动模型
    public static final String adminAccount = "admin";//管理员账号
    public static final String adminPassWord = "admin";//管理员密码
    public static final boolean duplicateCheck = true;//输入语句是否需要查重
    public static final int loginRequest = 1;//登录请求
    public static final int initMessage = 2;//初始化信息请求
    public static final int deleteSentenceType = 3;//删除语句分类
    public static final int agreeAdmin = 4;//同意申请账号
    public static final int addSentenceType = 5;//添加语句类别
    public static final int delSentence=6;//删除语句
}
