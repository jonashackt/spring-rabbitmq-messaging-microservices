package de.jonashackt.configuration;

import de.codecentric.namespace.weatherservice.WeatherService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
public class WeatherclientConfiguration {

    @Value("${weatherservice.host:localhost}")
    private String host;

    @Value("${weatherservice.port:8080}")
    private String port;

    @Value("${weatherservice.path")
    private final String path = "/api/weatherservice/soap/Weather";

    @Bean
    public WeatherService weatherServiceClient() {
        JaxWsProxyFactoryBean jaxWsFactory = new JaxWsProxyFactoryBean();
        jaxWsFactory.setServiceClass(WeatherService.class);
        jaxWsFactory.setAddress( "http://" + host + ":" + port + path);
        return (WeatherService) jaxWsFactory.create();
    }
}
