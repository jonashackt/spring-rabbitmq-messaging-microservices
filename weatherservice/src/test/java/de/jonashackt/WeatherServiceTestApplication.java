package de.jonashackt;

import de.jonashackt.configuration.WeatherServiceTestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    WeatherServiceApplication.class,
    WeatherServiceTestConfiguration.class
})
public class WeatherServiceTestApplication {

}
