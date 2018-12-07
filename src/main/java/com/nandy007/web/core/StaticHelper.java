package com.nandy007.web.core;

import com.nandy007.web.utils.RedisUtil;

public class StaticHelper{
    private static String serverPort;
    private static Integer sessionTimeout;
    private static RedisUtil redisUtil;


    /**
     * @return the redisUtil
     */
    public static RedisUtil getRedisUtil() {
        return redisUtil;
    }

    /**
     * @param redisUtil the redisUtil to set
     */
    public static void setRedisUtil(RedisUtil redisUtil) {
        StaticHelper.redisUtil = redisUtil;
    }


    public static String getServerPort(){
        return serverPort;
    }

    public static void setServerPort(String port) {
        StaticHelper.serverPort = port;
    }

     /**
     * @return the sessionTimeout
     */
    public static Integer getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * @param sessionTimeout the sessionTimeout to set
     */
    public static void setSessionTimeout(Integer sessionTimeout) {
        StaticHelper.sessionTimeout = sessionTimeout;
    }
}