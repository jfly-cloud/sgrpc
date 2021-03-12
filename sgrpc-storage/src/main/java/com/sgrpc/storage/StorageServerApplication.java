package com.sgrpc.storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class StorageServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageServerApplication.class, args);
        log.info("account is success!");
    }
}