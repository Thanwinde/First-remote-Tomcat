package com.datavisualization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Controller
@MapperScan("com.datavisualization.mapper")
@EnableTransactionManagement
public class DataVisualizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataVisualizationApplication.class, args);
    }
    /*
    @RequestMapping("/")
    public String demo(){
        return "Hello World";
    }
    */

    @RequestMapping("/")
    public String welcome(){
        return "star";
    }
    @RequestMapping("/welcome")
    public String te(){
        return "wel";
    }

}
