package com.rocky.appstockdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AppStockdataApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(AppStockdataApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppStockdataApplication.class);

        // Graceful shutdown 설정
        app.setRegisterShutdownHook(true);

        // 기본 속성 설정
        Map<String, Object> properties = new HashMap<>();
        properties.put("server.shutdown", "graceful");
        app.setDefaultProperties(properties);

        // 애플리케이션 실행 및 컨텍스트 획득
        ConfigurableApplicationContext context = app.run(args);

        // Shutdown hook 추가
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // 진행 중인 요청을 처리할 시간 부여
                Thread.sleep(2000);
                context.close();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
    }

}
