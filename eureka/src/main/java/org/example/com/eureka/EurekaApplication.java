package org.example.com.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*这是我的主启动类，从这里开始配置和扫描
* 包含@SpringBootConfiguration
* @EnableAutoCOnfigyration
* @ComponentScan*/
@SpringBootApplication
//把当前这个普通SpringBoot应用开启成Eureka Server
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        //SpringApplication是SpringBoot提供的一个启动工具类
        /*
        * 创建 Spring容器
        * 读取application.yml
        * 扫描组件
        * 执行自动配置
        * 启动内嵌web服务器
        * 让整个应用开始工作*/
        SpringApplication.run(EurekaApplication.class,args);
    }
}
