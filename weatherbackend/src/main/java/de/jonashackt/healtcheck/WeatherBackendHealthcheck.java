package de.jonashackt.healtcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.EventSimple;
import de.jonashackt.messaging.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SIMPLE;

@RestController("/healtcheck")
public class WeatherBackendHealthcheck {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendHealthcheck.class);

    @Autowired
    private MessageSender messageSender;

    @GetMapping
    public void sendSimpleEvent() throws JsonProcessingException {
        LOG.info("sendSimpleEvent() called, sending Message to 'weathersimple:queue'");

        EventSimple eventSimple = new EventSimple();
        eventSimple.setName("foo");

        messageSender.sendMessage(QUEUE_WEATHER_SIMPLE, eventSimple);
    }
}
