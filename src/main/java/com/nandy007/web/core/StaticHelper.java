package com.nandy007.web.core;

public class StaticHelper{
    private static String serverPort;

    public static String getServerPort(){
        return serverPort;
    }

    public static void setServerPort(String port){
        StaticHelper.serverPort = port;
    }
}