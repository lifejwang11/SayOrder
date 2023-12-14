package com.wlld.myjecs.mapper;


import com.wlld.myjecs.mesEntity.AdminSentence;
import com.wlld.myjecs.mesEntity.MyAdmin;
import com.wlld.myjecs.sqlEntity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SqlMapper {
    //展示分类的所有结构关系
    @Select("select sentence_id,word,type_id from sentence")
    List<Sentence> getModel();

    @Select("select * from keyword_type")
    List<KeywordType> getKeywordType();

    @Select("select * from keyword_sql")
    List<Keyword_sql> getKeywordSql();

    @Insert("insert into admin (account,pass_word,pass,name) values (#{account},#{pass_word},0,#{name})")
    void saveUser(MyAdmin myAdmin);

    @Insert("insert into sentence (word,type_id,date,adminID) values (#{word},#{type_id},#{date},#{adminID})")
    void saveSentence(Sentence sentence);

    @Select("SELECT MAX(sentence_id) AS sentence_id FROM sentence")
    int sentenceMaxID();

    @Insert("insert into keyword_sql (sentence_id,keyword,keyword_type_id) values (#{sentence_id},#{keyword},#{keyword_type_id})")
    void saveKeyword(Keyword_sql keywordSql);

    @Insert("insert into my_tree (title,sentence_nub) values (#{title},0)")
    void saveMyTree(MyTree myTree);

    @Select("SELECT MAX(type_id) AS type_id FROM my_tree")
    int maxTreeTypeID();

    @Insert("insert into keyword_type (type_id,keyword_mes,answer,type_number) values (#{type_id},#{keyword_mes},#{answer},0)")
    void saveKeywordType(KeywordType keywordType);

    @Select("select word from sentence where type_id=#{type_id} and word=#{word}")
    String different(Sentence sentence);

    @Select("select sentence_id,word,type_id from sentence where date=#{date} and adminID=#{adminID}")
    List<AdminSentence> getMySentenceByID(int adminID, String date);

    @Select("select * from admin where account=#{account}")
    Admin getAdminByAccount(String account);

    @Select("select * from admin where account=#{account} and pass_word=#{pass_word} and pass=1")
    Admin getAdmin(MyAdmin myAdmin);

    @Select("select * from my_tree")
    List<MyTree> getMyTree();

    @Select("select * from my_tree where type_id=#{type_id} ")
    MyTree getMyTreeByTypeID(int type_id);

    @Select("select * from keyword_type")
    List<KeywordType> getKeyWordType();

    @Select("select * from keyword_type where type_id=#{type_id}")
    List<KeywordType> getKeyWordTypeByTypeID(int type_id);

    @Select("select * from keyword_type where keyword_type_id=#{keyword_type_id}")
    KeywordType getKeyWordTypeByID(int keyword_type_id);

    @Select("select * from admin where pass=0")
    List<Admin> getAdminToPass();

    @Select("select * from admin where id=#{id}")
    Admin getAdminByID(int id);

    @Update("update my_tree set sentence_nub=#{sentence_nub} where type_id=#{type_id}")
    void updateSentenceNubByTypeID(int type_id, int sentence_nub);

    @Update("update admin set pass=1 where id=#{id}")
    void agreeAdminPass(int id);

    @Update("update keyword_type set type_number=#{type_number} where keyword_type_id=#{keyword_type_id}")
    void updateKeywordTypeNumberByID(int type_number, int keyword_type_id);

    @Delete("delete from admin where id = #{id}")
    void deleteAdmin(int id);

    @Delete("delete from keyword_sql where keyword_type_id=#{keyword_type_id}")
    void deleteKeyWordSqlByType(int keyword_type_id);

    @Delete("delete from keyword_sql where sentence_id=#{sentence_id}")
    void deleteKeyWordSqlBySentenceID(int sentence_id);

    @Delete("delete from keyword_type where type_id= #{type_id}")
    void deleteKeyWordTypeByID(int type_id);

    @Delete("delete from keyword_type where keyword_type_id= #{keyword_type_id}")
    void deleteKeyWordTypeByKey(int keyword_type_id);

    @Delete("delete from sentence where type_id =#{type_id}")
    void deleteSentenceByTypeID(int type_id);

    @Delete("delete from my_tree where type_id=#{type_id}")
    void delMyTreeByID(int type_id);

    @Delete("delete from sentence where sentence_id=#{sentence_id} and adminID=#{adminID}")
    void deleteSentenceByID(int adminID, int sentence_id);
}