package pers.jd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

// SpringBoot启动类
@SpringBootApplication
// @EnableEurekaClient表示注册为微服务
@EnableEurekaClient
// MP 包扫描
@MapperScan("pers.jd.mapper")
public class ErpCommodityApplication {

  public static void main(String[] args) {
    SpringApplication.run(ErpCommodityApplication.class, args);
  }

}