package de.jonashackt;

import de.codecentric.cxf.xmlvalidation.CustomFaultBuilder;
import de.jonashackt.fault.WeatherFaultBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class WeatherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherServiceApplication.class, args);
    }

    // Activating XML-Schema validation with custom Fault
    @Bean
    public CustomFaultBuilder weatherFaultBuilder() {
        return new WeatherFaultBuilder();
    }
}
