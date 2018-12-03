package com.nandy007.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nandy007.web.core.StaticHelper;

@SpringBootApplication
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		Application.showSuccessMsg();
	}

	private static void showSuccessMsg(){
		String n = "\n\n";
		String sep = n + "---------------------------------------------------------" + n;
		String msg = sep + "ChestnutApp启动完毕。端口：" + StaticHelper.getServerPort() + n + "请求时请注意带上头信息：'Authorization': 'Bearer ' + token" + sep;
		logger.info(msg);
	}
}
