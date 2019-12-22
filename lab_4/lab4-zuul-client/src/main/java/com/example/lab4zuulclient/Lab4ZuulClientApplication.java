package com.example.lab4zuulclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class Lab4ZuulClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lab4ZuulClientApplication.class, args);
	}

}
