package com.nandy007.web.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.nandy007.web.core.StaticHelper;

@Configuration
public class StaticConfig {
 
    @Value("${server.port}")
    private String serverPort;


    @Value("${session.timeout}")
    private Integer sessionTimeout;

 
    @Bean
    public int initStatic() {
        StaticHelper.setServerPort(serverPort);
        StaticHelper.setSessionTimeout(sessionTimeout);
        return 0;
    }
}