package de.jonashackt.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.EventGetOutlook;
import de.jonashackt.messaging.MessageSender;
import de.jonashackt.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;

@RestController
public class WeatherServiceEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceEndpoint.class);

    @Autowired
    private MessageSender messageSender;

    @PostMapping("/weather/forecast")
    public void forecast(@RequestBody Weather weather) throws JsonProcessingException {

        LOG.info("WeatherService invoked for new forecast");

        EventGetOutlook eventGetOutlook = new EventGetOutlook();
        eventGetOutlook.setWeather(weather);

        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, eventGetOutlook);
    }
}
