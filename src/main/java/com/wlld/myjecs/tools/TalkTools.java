package com.wlld.myjecs.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wlld.myjecs.bean.BeanMangerOnly;
import com.wlld.myjecs.config.Config;
import org.dromara.easyai.entity.CreatorModel;
import org.dromara.easyai.entity.SentenceModel;
import org.dromara.easyai.entity.TalkBody;
import org.dromara.easyai.entity.WordTwoVectorModel;
import org.dromara.easyai.naturalLanguage.TalkToTalk;
import org.dromara.easyai.naturalLanguage.Tokenizer;
import org.dromara.easyai.naturalLanguage.WordTemple;
import org.dromara.easyai.naturalLanguage.word.WordEmbedding;
import org.dromara.easyai.rnnJumpNerveCenter.CustomManager;
import org.dromara.easyai.transFormer.model.TransFormerModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TalkTools {
    public void initSemantics(BeanMangerOnly beanMangerOnly, List<TalkBody> sentences) throws Exception {
        List<TalkBody> sen = null;
        if (sentences != null) {
            sen = anySort(sentences);
        }
        initTalkToTalk(beanMangerOnly, sen);
    }

    private List<TalkBody> anySort(List<TalkBody> sentences) {//做乱序
        Random random = new Random();
        List<TalkBody> sent = new ArrayList<>();
        int time = sentences.size();
        for (int i = 0; i < time; i++) {
            int size = sentences.size();
            int index = random.nextInt(size);
            sent.add(sentences.get(index));
            sentences.remove(index);
        }
        return sent;
    }

    private void initTalkToTalk(BeanMangerOnly beanMangerOnly, List<TalkBody> sentences) throws Exception {
        File file = new File(Config.longTalkUrl); //创建文件
        TalkToTalk talkToTalk = beanMangerOnly.getTalkToTalk();
        if (file.exists()) {
            talkToTalk.insertModel(readCreatorModel2());
        } else {
            TransFormerModel transFormerModel = talkToTalk.study(sentences);
            String model = JSON.toJSONString(transFormerModel);
            writeModel(model, Config.longTalkUrl);
        }
    }


    private TransFormerModel readCreatorModel2() {
        File file = new File(Config.longTalkUrl);
        String a = readPaper(file);
        return JSONObject.parseObject(a, TransFormerModel.class);
    }


    private void writeModel(String model, String url) throws IOException {//写出模型与 激活参数
        OutputStreamWriter out = new OutputStreamWriter(Files.newOutputStream(Paths.get(url)), StandardCharsets.UTF_8);
        out.write(model);
        out.close();
    }

    private String readPaper(File file) {
        InputStream read = null;
        String context = null;
        try {
            read = Files.newInputStream(file.toPath());
            byte[] bt = new byte[read.available()];
            read.read(bt);
            context = new String(bt, StandardCharsets.UTF_8);
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close(); //确保关闭
                } catch (IOException el) {
                }
            }
        }
        return context;
    }
}
