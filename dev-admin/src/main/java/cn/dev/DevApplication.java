package cn.dev;

import cn.dev.common.config.DevConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author YangXw
 * @date 2023/01/18 20:54
 * @description
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DevApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  项目启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
