package com.wlld.myjecs.controller.vue;
 
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
 
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
 
/**
 * @author Admin
 */
@ServerEndpoint("/chat/{name}")
@Component
public class WebSocketService {
 
    static Logger log = LoggerFactory.getLogger(WebSocketService.class);
 
    private static int onlineCount = 0;
 
    private static ConcurrentHashMap<String, WebSocketService> webSocketServerMap = new ConcurrentHashMap<>();
 
    private Session session;
 
    private String name = "";
 
    private static ApplicationContext applicationContext;
 
    /**
     * 解决引入外部类方法
     * @param context
     */
    public static void setApplicationContext(ApplicationContext context){
        applicationContext = context;
    }
 
    /**
     * 建立连接
     * @param session
     * @param name
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("name") String name){
        this.session = session;
        webSocketServerMap.put(name,this);
        this.name = name;
        addOnlineCount();
        log.info("用户连接："+name+"，当前在线人数为："+getOnlineCount());
        try{
            sendMessage("进入聊天室成功");
        }catch (IOException e){
            log.error("用户："+name+",连接失败！！");
        }
    }
 
    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(){
        subOnlineCount();
        webSocketServerMap.remove(name);
        log.info("用户退出："+name+",当前在线人数为："+getOnlineCount());
    }
 
 
    @OnMessage
    public void onMessage(String message){
        log.info("用户消息："+name+",报文："+message);
        if(StrUtil.isNotBlank(message)){
            log.info(message);
            JSONObject jsonObject = JSON.parseObject(message);
            jsonObject.put("name",this.name);
            webSocketServerMap.forEach((u,m)->{
                if(name.contains(u)){
                    return;
                }
                try {
                    m.sendMessage(jsonObject.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
 
    /**
     * 服务器推送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
 
 
    /**
     * 获取在线人数
     * @return
     */
    public static  synchronized  int getOnlineCount(){
        return onlineCount;
    }
 
    /**
     * 上线
     */
    public static  synchronized  void addOnlineCount(){
        WebSocketService.onlineCount++;
    }
 
    /**
     * 离线
     */
    public static  synchronized void subOnlineCount(){
        WebSocketService.onlineCount--;
    }
 
}