package com.dailycodebuffer.departmentservice.config;

import com.dailycodebuffer.departmentservice.client.EmployeeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    @Autowired
//    private ExchangeFilterFunction filterFunction;
    private LoadBalancedExchangeFilterFunction filterFunction; //as its not load balanced

    @Bean
    public WebClient employeeWebClient(){
        LOGGER.info("******* WebClientConfig employeeClient *******  ",WebClient.builder()
                .baseUrl("http://EMPLOYEE-SERVICE")
//                .baseUrl("http://CCNL-10-NB-0757.cc-dk.net:employee-service:"+{port})
                .filter(filterFunction)
                .build());

        return WebClient.builder()
                .baseUrl("http://EMPLOYEE-SERVICE")
//                .baseUrl("http://CCNL-10-NB-0757.cc-dk.net:employee-service:"+{port})
                .filter(filterFunction)
                .build();
    }

    @Bean
    public EmployeeClient employeeClient(){
        LOGGER.info("******* WebClientConfig employeeClient *******");
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(employeeWebClient()))
                .build();
        LOGGER.info("******* httpServiceProxyFactory *******  ",httpServiceProxyFactory);
        LOGGER.info("******* httpServiceProxyFactory.createClient *******  ",httpServiceProxyFactory.createClient(EmployeeClient.class));
        return httpServiceProxyFactory.createClient(EmployeeClient.class);
    }
}
