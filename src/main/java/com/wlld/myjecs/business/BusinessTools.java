package com.wlld.myjecs.business;

import com.wlld.myjecs.mapper.SqlMapper;
import com.wlld.myjecs.entity.mes.UpKeyword;
import com.wlld.myjecs.entity.MyTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
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

    /**
     * 存储request session
     */
    public void setSessionValue(HttpServletRequest request, Integer adminId) {
        HttpSession session = request.getSession();
        log.info(request.getRequestURI() + " set sessionId={}", session.getId());
        request.getSession().setAttribute("adminId", adminId);
        // 设置session失效时间为30分钟
        request.getSession().setMaxInactiveInterval(1800);
    }

    public Object getSessionValue(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return null;
        }
        Object roleId = session.getAttribute("adminId");

        if (ObjectUtils.isEmpty(roleId)) {
            return null;
        }
        return roleId;
    }
}
