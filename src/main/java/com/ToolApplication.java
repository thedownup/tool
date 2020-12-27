package com;

import com.aywm.tool.exchange.biword.BiWordBootStrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolApplication {

    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(ToolApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);

        //币世界 技术监听
        BiWordBootStrap.start();

    }

}
