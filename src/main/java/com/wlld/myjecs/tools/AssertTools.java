package com.wlld.myjecs.tools;

import cn.hutool.core.lang.Assert;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.config.SayOrderConfig;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fn
 * @description 断言工具
 * @date 2024/3/27 11:09
 */
@UtilityClass
public class AssertTools {
    private static final Pattern WINDOWS_PATH_PATTERN = Pattern.compile("^[A-Za-z]:\\\\[^<>:\"|?*\\r\\n\\\\]+(\\\\[^<>:\"|?*\\r\\n\\\\]+)*\\\\?$");
    private static final Pattern LINUX_PATH_PATTERN = Pattern.compile("^\\/[^<>:\"|?*\\r\\n\\/]+(\\/[^<>:\"|?*\\r\\n\\/]+)*\\/?$");

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
    public static boolean checkPathValid(String path){
        if (isLinux()) {
            Matcher matcher = LINUX_PATH_PATTERN.matcher(path);
            return matcher.matches();
        }else if (isWindows()) {
            Matcher matcher = WINDOWS_PATH_PATTERN.matcher(path);
            return matcher.matches();
        }
        return false;
    }
    public boolean needTalkSql(SayOrderConfig sayOrderConfig) throws Exception {
        if (!checkPathValid(sayOrderConfig.getBaseDir())) {
            throw new IllegalStateException("路径非法");
        }
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
        if (!checkPathValid(sayOrderConfig.getBaseDir())) {
            throw new IllegalStateException("路径非法");
        }
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
        if (file1.exists() && file1.isFile()) {
            file1.delete();
        }
        if (file2.exists() && file2.isFile()) {
            file2.delete();
        }
        return true;
    }

    public boolean deleteSemantics(SayOrderConfig sayOrderConfig) throws Exception {
        Assert.notBlank(sayOrderConfig.getBaseDir(), "请先设置基本目录");
        File file1 = new File(sayOrderConfig.getBaseDir() +Config.Word2VecModelUrl);//词嵌入
        File file2 = new File(sayOrderConfig.getBaseDir() +Config.SentenceModelUrl);//语句模型
        File file3 = new File(sayOrderConfig.getBaseDir() +Config.onlyKeyWord);//关键词敏感性
        File file4 = new File(sayOrderConfig.getBaseDir() +Config.KeyWordModelUrl);//关键词捕捉
        File file5 = new File(sayOrderConfig.getBaseDir() +Config.keyWordIndex);//关键词
        if (file1.exists() && file1.isFile()) {
            file1.delete();
        }
        if (file2.exists() && file2.isFile()) {
            file2.delete();
        }
        if (file3.exists() && file3.isFile()) {
            file3.delete();
        }
        if (file4.exists() && file4.isFile()) {
            file4.delete();
        }
        if (file5.exists() && file5.isFile()) {
            file5.delete();
        }
        return true;
    }
}
