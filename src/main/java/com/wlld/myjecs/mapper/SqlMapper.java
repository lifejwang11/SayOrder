package com.wlld.myjecs.mapper;


import com.wlld.myjecs.entity.mes.AdminSentence;
import com.wlld.myjecs.entity.mes.MyAdmin;
import com.wlld.myjecs.entity.Admin;
import com.wlld.myjecs.entity.KeywordType;
import com.wlld.myjecs.entity.KeywordSql;
import com.wlld.myjecs.entity.MyTree;
import com.wlld.myjecs.entity.Sentence;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.wlld.entity.TalkBody;

import java.util.List;

@Mapper
public interface SqlMapper{
    //展示分类的所有结构关系
    @Select("select sentence_id,word,type_id from sentence")
    List<Sentence> getModel();

    @Select("select question,answer from q_a")
    List<TalkBody> getTalkModel();

    @Select("select * from keyword_type")
    List<KeywordType> getKeywordType();

    @Select("select * from keyword_sql")
    List<KeywordSql> getKeywordSql();

    @Insert("insert into admin (account,pass_word,pass,name) values (#{account},#{pass_word},0,#{name})")
    void saveUser(MyAdmin myAdmin);

    @Insert("insert into sentence (word,type_id,date,adminID) values (#{word},#{type_id},#{date},#{adminID})")
    void saveSentence(Sentence sentence);

    @Select("SELECT MAX(sentence_id) AS sentence_id FROM sentence")
    int sentenceMaxID();

    @Insert("insert into keyword_sql (sentence_id,keyword,keyword_type_id) values (#{sentence_id},#{keyword},#{keyword_type_id})")
    void saveKeyword(KeywordSql keywordSql);

    @Insert("insert into my_tree (title,sentence_nub) values (#{title},0)")
    void saveMyTree(MyTree myTree);

    @Select("SELECT MAX(type_id) AS type_id FROM my_tree")
    int maxTreeTypeID();

    @Insert("insert into keyword_type (type_id,keyword_mes,answer,type_number) values (#{type_id},#{keyword_mes},#{answer},0)")
    void saveKeywordType(KeywordType keywordType);

    @Select("select word from sentence where type_id=#{type_id} and word=#{word}")
    String different(Sentence sentence);

    @Select("select sentence_id,word,type_id from sentence where date=#{date} and adminID=#{adminID}")
    List<AdminSentence> getMySentenceByID(@Param("adminID")int adminID, @Param("date")String date);

    @Select("select * from admin where account=#{account}")
    Admin getAdminByAccount(@Param("account")String account);

    @Select("select * from admin where account=#{account} and pass_word=#{pass_word} and pass=1")
    Admin getAdmin(MyAdmin myAdmin);

    @Select("select * from my_tree")
    List<MyTree> getMyTree();

    @Select("select * from my_tree where type_id=#{type_id} ")
    MyTree getMyTreeByTypeID(@Param("type_id")int type_id);

    @Select("select * from keyword_type")
    List<KeywordType> getKeyWordType();

    @Select("select * from keyword_type where type_id=#{type_id}")
    List<KeywordType> getKeyWordTypeByTypeID(@Param("type_id")int type_id);

    @Select("select * from keyword_type where keyword_type_id=#{keyword_type_id}")
    KeywordType getKeyWordTypeByID(@Param("keyword_type_id")int keyword_type_id);

    @Select("select * from admin where pass=0")
    List<Admin> getAdminToPass();

    @Select("select * from admin where id=#{id}")
    Admin getAdminByID(@Param("id")int id);

    @Update("update my_tree set sentence_nub=#{sentence_nub} where type_id=#{type_id}")
    void updateSentenceNubByTypeID(@Param("type_id") int type_id,@Param("sentence_nub")  int sentence_nub);

    @Update("update admin set pass=1 where id=#{id}")
    void agreeAdminPass(@Param("id") int id);

    @Update("update keyword_type set type_number=#{type_number} where keyword_type_id=#{keyword_type_id}")
    void updateKeywordTypeNumberByID(@Param("type_number") int type_number, @Param("keyword_type_id") int keyword_type_id);

    @Delete("delete from admin where id = #{id}")
    void deleteAdmin(@Param("id")int id);

    @Delete("delete from keyword_sql where keyword_type_id=#{keyword_type_id}")
    void deleteKeyWordSqlByType(@Param("keyword_type_id")int keyword_type_id);

    @Delete("delete from keyword_sql where sentence_id=#{sentence_id}")
    void deleteKeyWordSqlBySentenceID(@Param("sentence_id")int sentence_id);

    @Delete("delete from keyword_type where type_id= #{type_id}")
    void deleteKeyWordTypeByID(@Param("type_id")int type_id);

    @Delete("delete from keyword_type where keyword_type_id= #{keyword_type_id}")
    void deleteKeyWordTypeByKey(@Param("keyword_type_id")int keyword_type_id);

    @Delete("delete from sentence where type_id =#{type_id}")
    void deleteSentenceByTypeID(@Param("type_id")int type_id);

    @Delete("delete from my_tree where type_id=#{type_id}")
    void delMyTreeByID(@Param("type_id")int type_id);

    @Delete("delete from sentence where sentence_id=#{sentence_id} and adminID=#{adminID}")
    void deleteSentenceByID(@Param("adminID")int adminID, @Param("sentence_id")int sentence_id);
}