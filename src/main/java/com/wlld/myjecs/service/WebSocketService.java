package com.wlld.myjecs.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wlld.myjecs.business.AiBusiness;
import com.wlld.myjecs.entity.mes.Response;
import com.wlld.myjecs.entity.mes.Shop;
import com.wlld.myjecs.entity.qo.SocketMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Admin
 */
@Getter
@Setter
@ServerEndpoint("/websocket/{name}")
@Component
@Slf4j
public class WebSocketService {
    private static ApplicationContext applicationContext;
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<String, WebSocketService> CLIENT = new ConcurrentHashMap<>();

    private Session session;

    private String name;

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        this.session = session;
        CLIENT.put(name, this);
        this.name = name;
        addOnlineCount();
        log.info("用户连接：{}，当前在线人数为：{}", name, getOnlineCount());
        try {
            SocketMessage<Object> msg = SocketMessage.builder()
                    .data(getOnlineCount())
                    .name(SocketMessage.AI)
                    .type(SocketMessage.INIT)
                    .build();
            sendMessage(JSONUtil.toJsonStr(msg));
        } catch (IOException e) {
            log.error("用户：{},连接失败！！", name);
        }
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        subOnlineCount();
        CLIENT.remove(name);
        log.info("用户退出：{},当前在线人数为：{}", name, getOnlineCount());
    }


    @OnMessage
    public void onMessage(String message) throws Exception {
        SocketMessage socketMessage = JSONUtil.toBean(message, SocketMessage.class);
        AiBusiness business = applicationContext.getBean(AiBusiness.class);
        String content = socketMessage.getContent();
        String type = socketMessage.getType();
        String name = socketMessage.getName();
        LocalDateTime now = LocalDateTime.now();
        String datatime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("用户消息：{},报文：{}", name, content);
        if (StrUtil.isNotBlank(content)) {
            CLIENT.forEach((u, m) -> {
                try {
                    if (u.equals(name)) {
                        if (SocketMessage.TALK.equals(type)) {
                            Response response = business.myTalk(content);
                            SocketMessage<Object> msg = SocketMessage.builder()
                                    .data(business.myTalk(content))
                                    .datetime(datatime)
                                    .content(response.getAnswer())
                                    .name(SocketMessage.AI)
                                    .type(SocketMessage.TALK)
                                    .build();
                            m.sendMessage(JSONUtil.toJsonStr(msg));
                        } else if (SocketMessage.SEMANTICS.equals(type)) {
                            Response response = business.talk(content);
                            SocketMessage<Object> msg = SocketMessage.builder()
                                    .data(business.talk(content))
                                    .datetime(datatime)
                                    .name(SocketMessage.AI)
                                    .type(SocketMessage.SEMANTICS)
                                    .build();
                            Shop shop = response.getShop();
                            if (shop != null) {
                                if (CollUtil.isNotEmpty(shop.getOrders())) {
                                    msg.setOrders(shop.getOrders());
                                }else{
                                    msg.setContent(shop.getAnswer());
                                }
                            }
                            m.sendMessage(JSONUtil.toJsonStr(msg));
                        }
                    }
                } catch (Exception e) {
                    SocketMessage<Object> msg = SocketMessage.builder()
                            .data("模型异常或未启动")
                            .name(SocketMessage.AI)
                            .type(SocketMessage.ERROR)
                            .build();
                    try {
                        m.sendMessage(JSONUtil.toJsonStr(msg));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }

    /**
     * 服务器推送消息
     */
    void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    void sendInfo(String message, @PathParam("name") String name) throws IOException {
        boolean sendAll = false;
        for (WebSocketService item : CLIENT.values()) {
            //这里可以设定只推送给这个sid的，为null则全部推送
            if (StrUtil.isBlank(name)) {
                item.sendMessage(message);
                if (sendAll) {
                    log.info("消息群发");
                }
                sendAll = true;
            } else if (item.getName().equals(name)) {
                log.info("推送消息给：{}，推送内容:{}", name, message);
                item.sendMessage(message);
            }
        }
    }

    /**
     * 获取在线人数
     */
    private synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 上线
     */
    private synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    /**
     * 离线
     */
    private synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

}