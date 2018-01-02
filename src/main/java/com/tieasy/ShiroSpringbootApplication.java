package com.tieasy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tieasy.mybatis.mapper") // mybatis包扫描注解，也可以直接在Mapper类上面添加注解@Mapper
public class ShiroSpringbootApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ShiroSpringbootApplication.class, args); // 最简单的写法
		SpringApplication app = new SpringApplication(ShiroSpringbootApplication.class);
		app.setBannerMode(Mode.OFF);// 关闭banner
		app.run(args);
	}
}
