package com.wlld.myjecs.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wlld.myjecs.bean.BeanMangerOnly;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.entity.business.*;
import org.wlld.entity.KeyWordForSentence;
import org.wlld.entity.SentenceModel;
import org.wlld.entity.WordTwoVectorModel;
import org.wlld.naturalLanguage.languageCreator.CatchKeyWord;
import org.wlld.naturalLanguage.word.MyKeyWord;
import org.wlld.naturalLanguage.word.WordEmbedding;
import org.wlld.rnnJumpNerveCenter.RRNerveManager;
import org.wlld.rnnJumpNerveCenter.RandomModel;
import org.wlld.rnnNerveCenter.ModelParameter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Tools {
    private void haveKeyWord(BeanMangerOnly beanMangerOnly, List<MySentence> sentences, boolean init) throws Exception {
        File file = new File(Config.onlyKeyWord); //创建文件
        Map<Integer, MyKeyWord> haveKeyWords = beanMangerOnly.getMyKeyWord();
        if (!file.exists() || init) {//模型文件不存在重新学习
            Map<Integer, List<KeyWordForSentence>> keyWordForSentenceMap = new HashMap<>();
            for (MySentence sentence : sentences) {
                List<KeyWordForSentence> keyWordForSentenceList;
                MyKeyWord myKeyWord;
                List<MyKeywordStudy> myKeywordStudyList = sentence.getMyKeywordStudyList();//关键词集合
                String word = sentence.getWord();
                for (MyKeywordStudy myKeywordStudy : myKeywordStudyList) {
                    int keyword_type_id = myKeywordStudy.getKeyword_type_id();//关键词id
                    if (haveKeyWords.containsKey(keyword_type_id)) {
                        keyWordForSentenceList = keyWordForSentenceMap.get(keyword_type_id);
                    } else {
                        keyWordForSentenceList = new ArrayList<>();
                        keyWordForSentenceMap.put(keyword_type_id, keyWordForSentenceList);
                        myKeyWord = new MyKeyWord(beanMangerOnly.getConfig(), beanMangerOnly.getWordEmbedding());
                        haveKeyWords.put(keyword_type_id, myKeyWord);
                    }
                    if (word != null) {
                        KeyWordForSentence keyWordForSentence = new KeyWordForSentence();
                        keyWordForSentence.setSentence(word);
                        keyWordForSentence.setKeyWord(myKeywordStudy.getKeyword());//不存在关键词也是一种训练，因为该模型的目的是关键词敏感性嗅探
                        keyWordForSentenceList.add(keyWordForSentence);
                    }
                }
            }
            HaveAllKeyWord haveAllKeyWord = new HaveAllKeyWord();
            List<HaveKey> haveKeyList = new ArrayList<>();
            haveAllKeyWord.setHaveKeyList(haveKeyList);
            for (Map.Entry<Integer, MyKeyWord> entry : haveKeyWords.entrySet()) {
                HaveKey haveKey = new HaveKey();
                int key = entry.getKey();
                ModelParameter modelParameter = entry.getValue().study(keyWordForSentenceMap.get(key));
                haveKey.setKey(key);
                haveKey.setModelParameter(modelParameter);
                haveKeyList.add(haveKey);
            }
            writeModel(JSONObject.toJSONString(haveAllKeyWord), Config.onlyKeyWord);
        } else {//模型文件存在直接读
            List<HaveKey> haveKeyList = readModelParameter().getHaveKeyList();//haveKeyWords
            for (HaveKey haveKey : haveKeyList) {
                MyKeyWord myKeyWord = new MyKeyWord(beanMangerOnly.getConfig(), beanMangerOnly.getWordEmbedding());
                myKeyWord.insertModel(haveKey.getModelParameter());
                haveKeyWords.put(haveKey.getKey(), myKeyWord);
            }
        }
    }

    private void keyWord(BeanMangerOnly beanMangerOnly, List<MySentence> sentences) throws IOException {//处理关键词
        File file = new File(Config.KeyWordModelUrl); //创建文件
        Map<Integer, CatchKeyWord> catchKeyWordMap = beanMangerOnly.catchKeyWord();
        if (!file.exists()) {//重新学习
            List<KeyWordModelMapping> keyWordModelMappings = new ArrayList<>();
            Map<Integer, List<KeySentence>> sentenceMap = new HashMap<>();
            for (MySentence sentence : sentences) {
                List<MyKeywordStudy> myKeywordStudyList = sentence.getMyKeywordStudyList();
                String word = sentence.getWord();
                for (MyKeywordStudy myKeywordStudy : myKeywordStudyList) {
                    String keyWord = myKeywordStudy.getKeyword();//关键词
                    int key = myKeywordStudy.getKeyword_type_id();
                    if (word != null && keyWord != null) {
                        KeySentence keySentence = new KeySentence();
                        keySentence.setWord(word);
                        keySentence.setKeyword(keyWord);
                        keySentence.setKeyword_type_id(key);
                        if (sentenceMap.containsKey(key)) {
                            sentenceMap.get(key).add(keySentence);
                        } else {
                            List<KeySentence> sentenceList = new ArrayList<>();
                            sentenceList.add(keySentence);
                            sentenceMap.put(key, sentenceList);
                        }
                    }
                }
            }
            for (Map.Entry<Integer, List<KeySentence>> entry : sentenceMap.entrySet()) {
                List<KeySentence> sentenceList = entry.getValue();
                int key = entry.getKey();
                List<KeyWordForSentence> keyWordForSentenceList = new ArrayList<>();
                CatchKeyWord catchKeyWord = new CatchKeyWord();
                catchKeyWordMap.put(key, catchKeyWord);//TODO 吃内存
                System.out.println("key:" + key);
                for (KeySentence sentence : sentenceList) {
                    KeyWordForSentence keyWordForSentence = new KeyWordForSentence();
                    keyWordForSentence.setSentence(sentence.getWord());
                    keyWordForSentence.setKeyWord(sentence.getKeyword());
                    keyWordForSentenceList.add(keyWordForSentence);
                }
                catchKeyWord.study(keyWordForSentenceList);//耗时的过程
                KeyWordModelMapping keyWordModelMapping = new KeyWordModelMapping();
                keyWordModelMapping.setKey(key);
                keyWordModelMapping.setKeyWordModel(catchKeyWord.getModel());
                keyWordModelMappings.add(keyWordModelMapping);
            }
            MyWordModel model = new MyWordModel();
            model.setKeyWordModelMappings(keyWordModelMappings);
            //模型写出
            writeModel(JSONObject.toJSONString(model), Config.KeyWordModelUrl);
        } else {//TODO 读取模型
            List<KeyWordModelMapping> keyWordModels = JSONObject.parseObject(readPaper(file), MyWordModel.class).getKeyWordModelMappings();
            for (KeyWordModelMapping keyWordModelMapping : keyWordModels) {
                int key = keyWordModelMapping.getKey();
                CatchKeyWord catchKeyWord = new CatchKeyWord();
                catchKeyWordMap.put(key, catchKeyWord);
                catchKeyWord.insertModel(keyWordModelMapping.getKeyWordModel());
            }
        }
    }

    private void allKeyWord(BeanMangerOnly beanMangerOnly, List<MySentence> sentences) throws IOException {
        File file = new File(Config.keyWordIndex);//关键词
        AllKeyWords allKeyWords = beanMangerOnly.getAllKeyWords();
        if (file.exists()) {
            List<KeyWord> keyWords = JSONObject.parseObject(readPaper(file), AllKeyWords.class).getKeyWords();
            allKeyWords.setKeyWords(keyWords);
        } else {
            List<KeyWord> keyWords = new ArrayList<>();
            allKeyWords.setKeyWords(keyWords);
            for (MySentence sentence : sentences) {
                List<MyKeywordStudy> myKeywordStudyList = sentence.getMyKeywordStudyList();
                String word = sentence.getWord();
                for (MyKeywordStudy myKeywordStudy : myKeywordStudyList) {
                    String keyword = myKeywordStudy.getKeyword();
                    if (word != null && keyword != null && !isSame(keyWords, keyword)) {
                        KeyWord keyWord = new KeyWord();
                        keyWord.setKeyword(keyword);
                        keyWord.setType(sentence.getType_id());
                        keyWords.add(keyWord);
                    }
                }
            }
            writeModel(JSONObject.toJSONString(allKeyWords), Config.keyWordIndex);
        }
    }

    private boolean isSame(List<KeyWord> keyWords, String word) {
        boolean isSame = false;
        for (KeyWord keyWord : keyWords) {
            if (keyWord.getKeyword().equals(word)) {
                isSame = true;
                break;
            }
        }
        return isSame;
    }

    public void initSemantics(BeanMangerOnly beanMangerOnly, List<MySentence> sentences, boolean test) throws Exception {
        if (sentences != null && !sentences.isEmpty()) {
            sentences = anySort(sentences);//语句乱序 有助于样本均衡
        }
        boolean isStudy = initWordEmbedding(beanMangerOnly, sentences);//初始化词向量嵌入
        initSentenceModel(beanMangerOnly, sentences, isStudy);//初始化语言模型
        haveKeyWord(beanMangerOnly, sentences, isStudy);//关键词敏感性嗅探模型
        keyWord(beanMangerOnly, sentences);//关键词抓取
        allKeyWord(beanMangerOnly, sentences);//关键字比较处理
        if (test) {//进行自检
            test(sentences, beanMangerOnly);
        }
    }

    private void test(List<MySentence> sentences, BeanMangerOnly beanMangerOnly) throws Exception {
        RRNerveManager randomNerveManager = beanMangerOnly.getRRNerveManager();
        int size = 0;
        if (sentences != null) {
            size = sentences.size();
        }
        int right = 0;
        for (int i = 0; i < size; i++) {
            int t = i + 1;
            MySentence sentence = sentences.get(i);
            int type = sentence.getType_id();
            if (randomNerveManager.getType(sentence.getWord(), t) == type) {
                right++;
            }
            double point = (double) right / (double) t;
            System.out.println("准确率:" + point + ",检测数量:" + t);
        }
    }

    private void initSentenceModel(BeanMangerOnly beanMangerOnly, List<MySentence> sentences, boolean isStudy) throws Exception {//初始化语言模型
        File file = new File(Config.SentenceModelUrl); //创建文件
        if (!file.exists() || isStudy) {//模型文件不存在，或者需要强制初始化，则进行初始化
            Map<Integer, List<String>> model = new HashMap<>();//语义识别
            for (MySentence sentence : sentences) {
                int type = sentence.getType_id();
                if (type > 0) {
                    String word = sentence.getWord();
                    if (model.containsKey(type)) {
                        model.get(type).add(word);
                    } else {
                        List<String> words = new ArrayList<>();
                        words.add(word);
                        model.put(type, words);
                    }
                }
            }
            RRNerveManager randomNerveManager = beanMangerOnly.getRRNerveManager();
            RandomModel mySentenceModel = randomNerveManager.studyType(model);
            String sentenceModel = JSON.toJSONString(mySentenceModel);
            writeModel(sentenceModel, Config.SentenceModelUrl);
        } else {//直接读文件
            beanMangerOnly.getRRNerveManager().insertModel(readSentenceModel());//初始化随机rnn网络集群
        }
    }

    private boolean initWordEmbedding(BeanMangerOnly beanMangerOnly, List<MySentence> sentences) throws Exception {//初始化词嵌入模型
        File file = new File(Config.Word2VecModelUrl); //创建文件
        WordEmbedding wordEmbedding = beanMangerOnly.getWordEmbedding();
        boolean isStudy = false;
        if (file.exists()) {//模型文件存在
            wordEmbedding.insertModel(readWord2VecModel(), beanMangerOnly.getConfig().getWordVectorDimension());//初始化word2Vec编码器
        } else if (sentences != null && !sentences.isEmpty()) {//模型文件不存在
            isStudy = true;
            SentenceModel sentenceModel = new SentenceModel();
            for (MySentence sentence : sentences) {
                sentenceModel.setSentence(sentence.getWord());
            }
            wordEmbedding.init(sentenceModel, beanMangerOnly.getConfig().getWordVectorDimension());
            WordTwoVectorModel wordTwoVectorModel = wordEmbedding.start();//词向量开始学习
            String model = JSON.toJSONString(wordTwoVectorModel);
            writeModel(model, Config.Word2VecModelUrl);
        } else {
            throw new Exception("训练数据为空");
        }
        return isStudy;
    }

    private List<MySentence> anySort(List<MySentence> sentences) {//做乱序
        Random random = new Random();
        List<MySentence> sent = new ArrayList<>();
        int time = sentences.size();
        for (int i = 0; i < time; i++) {
            int size = sentences.size();
            int index = random.nextInt(size);
            sent.add(sentences.get(index));
            sentences.remove(index);
        }
        return sent;
    }

    private RandomModel readSentenceModel() {
        File file = new File(Config.SentenceModelUrl);
        String a = readPaper(file);
        return JSONObject.parseObject(a, RandomModel.class);
    }

    private WordTwoVectorModel readWord2VecModel() {
        File file = new File(Config.Word2VecModelUrl);
        String a = readPaper(file);
        return JSONObject.parseObject(a, WordTwoVectorModel.class);
    }

    private HaveAllKeyWord readModelParameter() {
        File file = new File(Config.onlyKeyWord);
        String a = readPaper(file);
        return JSONObject.parseObject(a, HaveAllKeyWord.class);
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
