package com.viasoft.service_checker;

import com.viasoft.service_checker.services.impl.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCheckerApplication.class, args);
    }



}
