package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		try {
			log.info("正在启动应用程序...");
			SpringApplication.run(DemoApplication.class, args);
			log.info("应用程序启动成功！");
		} catch (Exception e) {
			log.error("应用程序启动失败：", e);
			throw e;
		}
	}

}
