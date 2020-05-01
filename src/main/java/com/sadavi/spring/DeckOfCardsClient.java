package com.sadavi.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.sadavi"})
public class DeckOfCardsClient {

    @Bean
    protected RestTemplate getTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args){
        SpringApplication.run(DeckOfCardsClient.class,  args);
    }
}
