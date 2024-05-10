package com.wlld.myjecs.tools;

import com.wlld.myjecs.config.Config;
import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * @author fn
 * @description 断言工具
 * @date 2024/3/27 11:09
 */
@UtilityClass
public class AssertTools {
    public boolean needTalkSql() {
        boolean isNeedSReadSql = true;
        File file1 = new File(Config.wordUrl);//词嵌入
        File file2 = new File(Config.talkUrl);//语句模型
        if (file1.exists() && file2.exists()) {
            isNeedSReadSql = false;
        }
        return isNeedSReadSql;
    }

    public boolean needReadSql() {
        boolean isNeedSReadSql = true;
        File file1 = new File(Config.Word2VecModelUrl);//词嵌入
        File file2 = new File(Config.SentenceModelUrl);//语句模型
        File file3 = new File(Config.onlyKeyWord);//关键词敏感性
        File file4 = new File(Config.KeyWordModelUrl);//关键词捕捉
        File file5 = new File(Config.keyWordIndex);//关键词
        if (file1.exists() && file2.exists() && file3.exists() && file4.exists() && file5.exists()) {
            isNeedSReadSql = false;
        }
        return isNeedSReadSql;
    }
}
