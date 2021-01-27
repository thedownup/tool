package com;

import com.aywm.tool.exchange.biword.BiWordBootStrap;
import com.aywm.tool.othersoft.sapher.youtubedl.YoutubeDLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolApplication {

    public static void main(String[] args) throws YoutubeDLException {
        final SpringApplication springApplication = new SpringApplication(ToolApplication.class);
        springApplication.run(args);
        //币世界 技术指标监听
        BiWordBootStrap.start();
    }

}
