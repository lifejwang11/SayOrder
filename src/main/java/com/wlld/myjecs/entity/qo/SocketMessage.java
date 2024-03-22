package com.wlld.myjecs.entity.qo;

import com.wlld.myjecs.entity.mes.Order;
import com.wlld.myjecs.entity.mes.Shop;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author fn
 * @description
 * @date 2024/3/22 10:08
 */
@Data
@Builder
public class SocketMessage<T> {
    public static final String AI = "AI";
    public static final String INIT = "init";//初始化
    public static final String ERROR = "error";//初始化
    public static final String TALK = "talk";//对话
    public static final String SEMANTICS = "semantics";//语义
    private T data;
    //语义订单或者对话 TALK or SEMANTICS
    private String type;
    private String content;
    //ai or user
    private String name;
    private String datetime;
    private List<Order> orders;

}
