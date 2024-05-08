package com.wlld.myjecs.business;

import com.wlld.myjecs.bean.BeanMangerOnly;
import com.wlld.myjecs.config.ErrorCode;
import com.wlld.myjecs.entity.business.KeyWord;
import com.wlld.myjecs.entity.mes.Order;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.mes.Shop;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.tools.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlld.naturalLanguage.languageCreator.CatchKeyWord;
import org.wlld.naturalLanguage.word.MyKeyWord;
import org.wlld.rnnJumpNerveCenter.CustomManager;
import org.wlld.rnnJumpNerveCenter.RRNerveManager;

import java.util.*;

@Service
public class AiBusiness {
    @Autowired
    private BeanMangerOnly beanMangerOnly;

    public Response myTalk(String word) throws Exception {
        Response response = new Response();
        CustomManager customManager = beanMangerOnly.getCustomManager();
        String answer = customManager.getAnswer(word, SnowflakeIdWorker.get().nextId());
        if (answer != null) {
            response.setAnswer(answer);
        } else {
            response.setAnswer("抱歉，我们还没有相关服务，请等待探仔下一步更新");
        }
        return response;
    }

    public Response talk(String word) throws Exception {//对话返回分类
        Response response = new Response();
        if (word != null && word.replace(" ", "").length() > 1) {
            Map<Integer, List<KeywordType>> kts = beanMangerOnly.getKeyTypes();//关键词模型与语义类别映射关系
            Map<Integer, CatchKeyWord> catchKeyWordMap = beanMangerOnly.catchKeyWord();//关键词抓取模型
            Map<Integer, MyKeyWord> myKeyWordMap = beanMangerOnly.getMyKeyWord();//关键词嗅探模型
            long eventId = SnowflakeIdWorker.get().nextId();//保证线程安全
            Shop shop = new Shop();//协议
            RRNerveManager rrNerveManager = beanMangerOnly.getRRNerveManager();//语言模型
            KeyWord keyWord = keyWordEq(word);
            int type;
            if (keyWord == null) {
                type = rrNerveManager.getType(word, eventId);
                if (type > 0) {
                    List<KeywordType> keywordTypeList = kts.get(type);
                    List<Order> orders = new ArrayList<>();
                    shop.setOrders(orders);
                    shop.setType_id(type);
                    for (KeywordType keywordType : keywordTypeList) {
                        int keyword_type_id = keywordType.getKeyword_type_id();
                        boolean isKey = myKeyWordMap.get(keyword_type_id).isKeyWord(word, eventId);//是否有关键词
                        if (isKey) {
                            CatchKeyWord catchKeyWord = catchKeyWordMap.get(keyword_type_id);
                            if (catchKeyWord != null) {
                                Order order = new Order();
                                Set<String> keyWords = catchKeyWord.getKeyWord(word);
                                if (!keyWords.isEmpty()) {
                                    order.setKeyWords(keyWords);
                                    order.setKeyword_type_id(keyword_type_id);
                                    orders.add(order);
                                } else {
                                    if (shop.getAnswer() == null) {
                                        shop.setAnswer(keywordType.getAnswer());
                                    } else {
                                        shop.setAnswer(shop.getAnswer() + "," + keywordType.getAnswer());
                                    }
                                }
                            }
                        } else {
                            if (shop.getAnswer() == null) {
                                shop.setAnswer(keywordType.getAnswer());
                            } else {
                                shop.setAnswer(shop.getAnswer() + "," + keywordType.getAnswer());
                            }
                        }
                    }
                    shop.setFree(isFree(word));
                } else {
                    shop.setAnswer("目前还无法提供该服务！");
                    shop.setType_id(type);
                }
            } else {//直接检查出关键词
                type = keyWord.getType();
                shop.setType_id(type);
                shop.setKeyword(word);
                shop.setIndex(keyWord.getIndex());
            }
            response.setShop(shop);
        } else {
            response.setError(ErrorCode.WordIsNull.getError());
            response.setErrorMessage(ErrorCode.WordIsNull.getErrorMessage());
        }
        return response;
    }

    private KeyWord keyWordEq(String word) {
        KeyWord myKeyWord = null;
        List<KeyWord> keyWordList = beanMangerOnly.getAllKeyWords().getKeyWords();
        for (KeyWord keyWord : keyWordList) {
            if (keyWord.getKeyword().equals(word)) {
                myKeyWord = keyWord;
                break;
            }
        }
        return myKeyWord;
    }

    private boolean isFree(String myWord) {//是否有折扣要求
        boolean isFree = false;
        List<String> freeWords = beanMangerOnly.getFreeWord();
        for (String word : freeWords) {
            if (myWord.contains(word)) {
                isFree = true;
                break;
            }
        }
        return isFree;
    }
}
