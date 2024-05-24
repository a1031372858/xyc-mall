package org.xyc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@EnableFeignClients(basePackages = Application.XYC)
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = Application.XYC)
public class Application
{
    public final static String XYC = "org.xyc";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
