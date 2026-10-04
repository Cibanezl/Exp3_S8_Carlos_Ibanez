package com.duoc.banco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BancoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BancoApplication.class, args);
    }
}