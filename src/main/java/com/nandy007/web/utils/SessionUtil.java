package com.nandy007.web.utils;

import com.nandy007.web.core.StaticHelper;
import com.nandy007.web.model.SessionInfo;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class SessionUtil {

    /**
     * 获取request
     * @return
     */
    private static HttpServletRequest getHttpRequest(){  
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes(); 
        return requestAttributes==null ? null : requestAttributes.getRequest();  
    }


    private static RedisUtil getRedisUtil(){
        RedisUtil redisUtil = StaticHelper.getRedisUtil();
        return redisUtil;
    };


    private static Integer getTTL(){
        return StaticHelper.getSessionTimeout();
    }

    private static void setRedisSessionInfo(SessionInfo sessionInfo){
        String sessionId = getSessionId();
        getRedisUtil().set(sessionId, sessionInfo, getTTL());
    }

    private static SessionInfo getRedisSessionInfo(){
        String sessionId = getSessionId();
        SessionInfo sessionInfo = (SessionInfo) getRedisUtil().get(sessionId);
        return sessionInfo;
    }

    private static SessionInfo getRedisSessionInfo(String sessionId){
        SessionInfo sessionInfo = (SessionInfo) getRedisUtil().get(sessionId);
        return sessionInfo;
    }


    public static String getSessionId(String token, HttpServletRequest request){
        if(token==null){
            throwError();
        }
        request.getSession().setAttribute("authToken", token);
        String sessionId = MD5Util.MD5Encode(token);
        return sessionId;
    }


    public static String getSessionId(){

        String token = (String) getHttpRequest().getSession().getAttribute("authToken");

        if(token==null){
            throwError();
        }

        String sessionId = MD5Util.MD5Encode(token);

        return sessionId;
    }

    public static void setSessionInfo(SessionInfo sessionInfo){
        getHttpRequest().getSession().setAttribute("authToken", sessionInfo.getToken());
        setRedisSessionInfo(sessionInfo);
    }

    public static SessionInfo getSessionInfo(){
        return getRedisSessionInfo();
    }

    public static SessionInfo getSessionInfo(String sessionId){
        return getRedisSessionInfo(sessionId);
    }

    public static void set(String key, Object value){
        SessionInfo sessionInfo = getRedisSessionInfo();
        if(sessionInfo==null){
            throwError();
        }
        ReflexObjectUtil.setValue(sessionInfo, key, value);
        setRedisSessionInfo(sessionInfo);
    }

    public static Object get(String key){
        SessionInfo sessionInfo = getRedisSessionInfo();
        Object obj = ReflexObjectUtil.getValueByKey(sessionInfo, key);
        return obj;
    }

    private static void throwError(){
        throw new Error("会话为空");
    }
    
    
}