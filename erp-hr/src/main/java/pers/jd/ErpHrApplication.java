package pers.jd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// SpringBoot启动类
@SpringBootApplication
// @EnableEurekaClient表示注册为微服务
@EnableEurekaClient
// MP 包扫描
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("pers.jd.mapper")
public class ErpHrApplication {

  public static void main(String[] args) {
    SpringApplication.run(ErpHrApplication.class, args);
  }

}