package com.hoangtien2k3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableDiscoveryClient
//@ComponentScan({"com.hoangtien2k3.profileservice", "com.hoangtien2k3.commonservice"})
public class ProfileserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileserviceApplication.class, args);
	}

}
