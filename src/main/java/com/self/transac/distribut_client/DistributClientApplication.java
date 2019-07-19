package com.self.transac.distribut_client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
@MapperScan(basePackages = {"com.self.transac.distribut_client.dao"})
@EnableAutoConfiguration
public class DistributClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributClientApplication.class, args);
	}

}
