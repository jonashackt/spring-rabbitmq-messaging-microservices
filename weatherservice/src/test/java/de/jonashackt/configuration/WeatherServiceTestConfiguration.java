package de.jonashackt.configuration;

import de.jonashackt.adapter.WeatherBackendAdapter;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.cxf.configuration.CxfAutoConfiguration;
import de.codecentric.cxf.soaprawclient.SoapRawClient;
import de.codecentric.namespace.weatherservice.WeatherService;

@Configuration
@PropertySource("classpath:dev-test.properties")
public class WeatherServiceTestConfiguration {

    @Value("${webservice.client.port}")
    private String clientPort;
    
    @Value("${webservice.client.host}")
    private String clientHost;
    
    // Mind the "static"
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    /*
     * CXF JaxWs Client
     */
    @Bean
    public WeatherService weatherServiceClient() {
        JaxWsProxyFactoryBean jaxWsFactory = new JaxWsProxyFactoryBean();
        jaxWsFactory.setServiceClass(WeatherService.class);
        jaxWsFactory.setAddress(buildUrl());
        return (WeatherService) jaxWsFactory.create();
    }
    
    public String buildUrl() {
        // return something like http://localhost:8084/soap-api/WeatherSoapService
        return "http://" + clientHost + ":" + clientPort + cxfAutoConfiguration.baseAndServiceEndingUrl();
    }
    
    @Autowired
    private CxfAutoConfiguration cxfAutoConfiguration;

    @Bean
    public WeatherBackendAdapter weatherBackendAdapter() {
        // We want to prevent integration-testing the real REST services from weatherbackend and use the stub instead
        return new WeatherBackendAdapterStub();
    }
}
