package com.wlld.myjecs.tools;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.config.SayOrderConfig;
import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * @author fn
 * @description 断言工具
 * @date 2024/3/27 11:09
 */
@UtilityClass
public class AssertTools {
    public boolean needTalkSql(SayOrderConfig sayOrderConfig) throws Exception {
        Assert.notBlank(sayOrderConfig.getBaseDir(), "请先设置基本目录");
        boolean isNeedSReadSql = true;
        File file1 = new File(sayOrderConfig.getBaseDir() + Config.wordUrl);//词嵌入
        File file2 = new File(sayOrderConfig.getBaseDir() + Config.talkUrl);//语句模型
        if (file1.exists() && file2.exists()) {
            isNeedSReadSql = false;
        }
        return isNeedSReadSql;
    }

    public boolean needReadSql(SayOrderConfig sayOrderConfig) throws Exception {
        Assert.notBlank(sayOrderConfig.getBaseDir(), "请先设置基本目录");
        boolean isNeedSReadSql = true;
        File file1 = new File(sayOrderConfig.getBaseDir() +Config.Word2VecModelUrl);//词嵌入
        File file2 = new File(sayOrderConfig.getBaseDir() +Config.SentenceModelUrl);//语句模型
        File file3 = new File(sayOrderConfig.getBaseDir() +Config.onlyKeyWord);//关键词敏感性
        File file4 = new File(sayOrderConfig.getBaseDir() +Config.KeyWordModelUrl);//关键词捕捉
        File file5 = new File(sayOrderConfig.getBaseDir() +Config.keyWordIndex);//关键词
        if (file1.exists() && file2.exists() && file3.exists() && file4.exists() && file5.exists()) {
            isNeedSReadSql = false;
        }
        return isNeedSReadSql;
    }

    public boolean deleteTalk(SayOrderConfig sayOrderConfig) throws Exception {
        Assert.notBlank(sayOrderConfig.getBaseDir(), "请先设置基本目录");
        File file1 = new File(sayOrderConfig.getBaseDir() + Config.wordUrl);//词嵌入
        File file2 = new File(sayOrderConfig.getBaseDir() + Config.talkUrl);//语句模型
        if (file1.exists() && file2.exists()) {
            file1.delete();
            return file2.delete();
        }
        return false;
    }

    public boolean deleteSemantics(SayOrderConfig sayOrderConfig) throws Exception {
        Assert.notBlank(sayOrderConfig.getBaseDir(), "请先设置基本目录");
        File file1 = new File(sayOrderConfig.getBaseDir() +Config.Word2VecModelUrl);//词嵌入
        File file2 = new File(sayOrderConfig.getBaseDir() +Config.SentenceModelUrl);//语句模型
        File file3 = new File(sayOrderConfig.getBaseDir() +Config.onlyKeyWord);//关键词敏感性
        File file4 = new File(sayOrderConfig.getBaseDir() +Config.KeyWordModelUrl);//关键词捕捉
        File file5 = new File(sayOrderConfig.getBaseDir() +Config.keyWordIndex);//关键词
        if (file1.exists() && file2.exists() && file3.exists() && file4.exists() && file5.exists()) {
            file1.delete();
            file2.delete();
            file3.delete();
            file4.delete();
           return file5.delete();
        }
        return false;
    }


}
