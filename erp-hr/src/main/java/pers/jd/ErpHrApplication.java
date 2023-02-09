package pers.jd;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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

        int port = 8991;
        int eurekaServerPort = 8888;

        // 检测微服务服务端是否启动
        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort);
            System.exit(1);
        }

        /**
         * 这一段就表示启动的时候如果带了参数，
         * 如： port=8099,
         * 那么程序就会使用 8099 作为端口号了。
         * 这样做的好处是，什么代码都不用改，
         * 只需要在启动的时候带不同的参数，
         * 就可以启动不同的端口了。
         */
        if (null != args && 0 != args.length) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(ErpHrApplication.class).properties("server.port=" + port).run(args);

    }

}