package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)// springboot启动时排除数据源
public class SpringBootRun2 {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun2.class,args);
	}
}
