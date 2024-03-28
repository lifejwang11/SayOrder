package com.wlld.myjecs.business;

import com.wlld.myjecs.access.SessionCreator;
import com.wlld.myjecs.bean.BeanMangerOnly;
import com.wlld.myjecs.config.Config;
import com.wlld.myjecs.config.ErrorCode;
import com.wlld.myjecs.mapper.SqlMapper;
import com.wlld.myjecs.entity.mes.KeywordTypeMessage;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.mes.SentenceTypeAndKeyword;
import com.wlld.myjecs.entity.mes.SubmitSentence;
import com.wlld.myjecs.entity.mes.UpKeyword;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DataBusiness {
    @Autowired
    private BusinessTools businessTools;
    @Autowired
    private BeanMangerOnly beanMangerOnly;
    @Autowired
    private SqlMapper sqlMapper;

    @Transactional
    public synchronized Response addSentence(SubmitSentence submitSentence, HttpServletResponse res) {
        Response response = new Response();
        int typeID = submitSentence.getType_id();
        String typeName = businessTools.typeIDOk(typeID);
        boolean contain = true;
        if (!submitSentence.getUpKeywordList().isEmpty()) {
            contain = businessTools.containKeyword(submitSentence.getUpKeywordList(), submitSentence.getWord());
        }
        if (typeName != null && contain) {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String myDate = simpleDateFormat.format(date);//今日时间
            Sentence sentence = new Sentence();
            sentence.setWord(submitSentence.getWord());
            sentence.setType_id(submitSentence.getType_id());
            sentence.setDate(myDate);
            sentence.setAdminID(SessionCreator.getAdmin());
            boolean success = false;
            int sentence_id = 0;
            if (Config.duplicateCheck) {//需要查重
                if (sqlMapper.different(sentence) == null) {
                    int sentenceNub = sqlMapper.getMyTreeByTypeID(typeID).getSentence_nub();
                    sqlMapper.saveSentence(sentence);
                    sentence_id = sqlMapper.sentenceMaxID();
                    sqlMapper.updateSentenceNubByTypeID(typeID, sentenceNub + 1);//修改该分类语句数量
                    success = true;
                    response.setError(ErrorCode.OK.getError());
                    response.setErrorMessage(ErrorCode.OK.getErrorMessage());
                } else {
                    response.setError(ErrorCode.invalidSentence.getError());
                    response.setErrorMessage(ErrorCode.invalidSentence.getErrorMessage());
                }
            } else {
                sqlMapper.saveSentence(sentence);
                sentence_id = sqlMapper.sentenceMaxID();
                response.setError(ErrorCode.OK.getError());
                response.setErrorMessage(ErrorCode.OK.getErrorMessage());
                success = true;
            }
            List<UpKeyword> upKeywordList = submitSentence.getUpKeywordList();
            if (success && !upKeywordList.isEmpty()) {//语句添加完毕，检查关键词
                for (UpKeyword upKeyword : upKeywordList) {
                    KeywordType keywordType = sqlMapper.getKeyWordTypeByID(upKeyword.getKeyword_type_id());
                    if (keywordType != null) {
                        int keywordNumber = keywordType.getType_number();
                        KeywordSql keywordSql = new KeywordSql();
                        keywordSql.setSentence_id(sentence_id);
                        keywordSql.setKeyword(upKeyword.getKeyword());
                        keywordSql.setKeyword_type_id(upKeyword.getKeyword_type_id());
                        sqlMapper.saveKeyword(keywordSql);
                        sqlMapper.updateKeywordTypeNumberByID(keywordNumber + 1, upKeyword.getKeyword_type_id());
                    }
                }
            }
        } else {//不存在找个类型
            response.setError(ErrorCode.invalidTypeID.getError());
            response.setErrorMessage(ErrorCode.invalidTypeID.getErrorMessage());
        }
        return response;
    }

    @Transactional
    public Response delSentence(HttpServletResponse res, int sentence_id) {
        Response response = new Response();
        int adminID = SessionCreator.getAdmin();
        sqlMapper.deleteSentenceByID(adminID, sentence_id);
        sqlMapper.deleteKeyWordSqlBySentenceID(sentence_id);
        response.setResultID(sentence_id);
        response.setResponseType(Config.delSentence);
        response.setError(ErrorCode.OK.getError());
        response.setErrorMessage(ErrorCode.OK.getErrorMessage());
        return response;
    }

    @Transactional
    public Response delKeywordType(HttpServletResponse request, int keyword_type_id) {
        Response response = new Response();
        if (SessionCreator.getAdmin() == -1) {
            KeywordType keywordType = sqlMapper.getKeyWordTypeByID(keyword_type_id);
            if (keywordType != null && keywordType.getType_number() < beanMangerOnly.sysConfig().getDelKeywordNubTh()) {
                sqlMapper.deleteKeyWordSqlByType(keyword_type_id);
                sqlMapper.deleteKeyWordTypeByKey(keyword_type_id);
                response.setError(ErrorCode.OK.getError());
                response.setErrorMessage(ErrorCode.OK.getErrorMessage());
            } else {
                response.setError(ErrorCode.sentenceNubTooMuch.getError());
                response.setErrorMessage(ErrorCode.sentenceNubTooMuch.getErrorMessage());
            }
        } else {
            response.setError(ErrorCode.NotPower.getError());
            response.setErrorMessage(ErrorCode.NotPower.getErrorMessage());
        }
        return response;
    }

    @Transactional
    public Response delSentenceType(HttpServletResponse request, int type_id) {
        Response response = new Response();
        if (SessionCreator.getAdmin() == -1) {
            MyTree myTree = sqlMapper.getMyTreeByTypeID(type_id);
            response.setResponseType(Config.deleteSentenceType);
            if (myTree != null) {
                if (myTree.getSentence_nub() < beanMangerOnly.sysConfig().getDelSentenceNubTh()) {
                    List<KeywordType> keywordTypeList = sqlMapper.getKeyWordTypeByTypeID(type_id);
                    if (!keywordTypeList.isEmpty()) {
                        for (KeywordType keywordType : keywordTypeList) {
                            sqlMapper.deleteKeyWordSqlByType(keywordType.getKeyword_type_id());
                        }
                        sqlMapper.deleteKeyWordTypeByID(type_id);
                    }
                    sqlMapper.deleteSentenceByTypeID(type_id);
                    sqlMapper.delMyTreeByID(type_id);
                    response.setResultID(type_id);
                    response.setError(ErrorCode.OK.getError());
                    response.setErrorMessage(ErrorCode.OK.getErrorMessage());
                } else {
                    response.setError(ErrorCode.sentenceNubTooMuch.getError());
                    response.setErrorMessage(ErrorCode.sentenceNubTooMuch.getErrorMessage());
                }
            }
        } else {
            response.setError(ErrorCode.NotPower.getError());
            response.setErrorMessage(ErrorCode.NotPower.getErrorMessage());
        }
        return response;
    }

    @Transactional
    public synchronized Response addSentenceType(HttpServletResponse request, SentenceTypeAndKeyword sentenceTypeAndKeyword) {
        Response response = new Response();
        if (SessionCreator.getAdmin() == -1) {//管理员
            String title = sentenceTypeAndKeyword.getTitle();//类别描述
            List<KeywordTypeMessage> keywordTypeMessages = sentenceTypeAndKeyword.getKeywordTypeMessages();
            MyTree myTree = new MyTree();
            myTree.setTitle(title);
            sqlMapper.saveMyTree(myTree);
            int type_id = sqlMapper.maxTreeTypeID();
            if (!keywordTypeMessages.isEmpty()) {//插入关键词类别
                for (KeywordTypeMessage keywordType : keywordTypeMessages) {
                    KeywordType myKeywordType = new KeywordType();
                    myKeywordType.setType_id(type_id);
                    myKeywordType.setKeyword_mes(keywordType.getKeyword_mes());
                    myKeywordType.setAnswer(keywordType.getAnswer());
                    sqlMapper.saveKeywordType(myKeywordType);
                }
            }
            response.setResponseType(Config.addSentenceType);
            response.setError(ErrorCode.OK.getError());
            response.setErrorMessage(ErrorCode.OK.getErrorMessage());
        } else {
            response.setError(ErrorCode.NotPower.getError());
            response.setErrorMessage(ErrorCode.NotPower.getErrorMessage());
        }
        return response;
    }
}
