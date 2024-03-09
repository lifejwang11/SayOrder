package com.wlld.myjecs.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
public class WlldSession implements Runnable {
    private static final WlldSession SESSION = new WlldSession();
    private final Map<Long, Body> sessionMap = new ConcurrentHashMap<>();
    private int liveTime = 60 * 60 * 12;//12小时

    public void setSessionTime(int sessionTime) {//设置session时间
        liveTime = sessionTime;
    }

    static class Body {
        private Map<String, Object> value = new HashMap<>();
        private int time;
    }

    private WlldSession() {
    }

    public static WlldSession getSESSION() {
        return SESSION;
    }


    /**
     * header存储tokenID，本地缓存记录角adminId
     *
     * @param res
     * @param key
     * @param value
     */
    public void setValue(HttpServletResponse res, String key, Object value) {
        String token = res.getHeader("tokenID");
        if (token != null) {
            long tokenId = Long.parseLong(token);
            Body body = sessionMap.get(tokenId);
            if (body != null) {
                body.value.put(key, value);
            }
        }
    }

    public Object getValue(HttpServletResponse res, String key) {
        String token = res.getHeader("tokenID");
        if (token != null) {
            long tokenId = Long.parseLong(token);
            Body body = sessionMap.get(tokenId);
            if (body != null) {
                return body.value.get(key);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean isLife(String tokenID) {
        Pattern pattern = Pattern.compile("^-?[0-9]+");
        Matcher isNum = pattern.matcher(tokenID);
        if (isNum.matches()) {
            return sessionMap.containsKey(Long.parseLong(tokenID));
        } else {
            return false;
        }
    }

    public long createSession() {
        long id = IdCreator.get().nextId();
        sessionMap.put(id, new Body());
        return id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (Map.Entry<Long, Body> entry : sessionMap.entrySet()) {
                    Body body = entry.getValue();
                    long key = entry.getKey();
                    int time = body.time + 1;
                    if (time > liveTime) {
                        sessionMap.remove(key);
                    } else {
                        body.time = time;
                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
