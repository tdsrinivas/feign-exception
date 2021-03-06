package com.srini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FeignExceptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignExceptionApplication.class, args);
	}
}
