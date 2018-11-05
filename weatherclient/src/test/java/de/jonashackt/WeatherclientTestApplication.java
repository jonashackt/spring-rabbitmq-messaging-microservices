package de.jonashackt;

import de.codecentric.namespace.weatherservice.WeatherService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherclientTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherclientTestApplication.class, args);
	}

	@Bean
	public WeatherService weatherServiceClient() {
		return new WeatherServiceMock();
	}
}
