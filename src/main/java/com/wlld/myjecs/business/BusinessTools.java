package com.wlld.myjecs.business;

import com.wlld.myjecs.mapper.SqlMapper;
import com.wlld.myjecs.mesEntity.UpKeyword;
import com.wlld.myjecs.sqlEntity.MyTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTools {
    @Autowired
    private SqlMapper sqlMapper;

    public String typeIDOk(int id) {
        List<MyTree> myTrees = sqlMapper.getMyTree();//所有分类
        String typeName = null;
        for (MyTree myTree : myTrees) {
            if (myTree.getType_id() == id) {
                typeName = myTree.getTitle();
                break;
            }
        }
        return typeName;
    }

    public boolean containKeyword(List<UpKeyword> upKeywordList, String word) {
        boolean contain = true;
        for (UpKeyword upKeyword : upKeywordList) {
            if (!word.contains(upKeyword.getKeyword())) {
                contain = false;
                break;
            }
        }
        return contain;
    }
}
