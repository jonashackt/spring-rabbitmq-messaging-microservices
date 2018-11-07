package de.jonashackt.healtcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.EventSimple;
import de.jonashackt.messaging.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static de.jonashackt.common.ModelUtil.exampleEventGetOutlook;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SIMPLE;

/*
 * Access this REST API with
 * curl -v localhost:8095/healthcheck
 * curl -v localhost:8095/event
 * curl -v localhost:8095/events/100
 */
@RestController("/healtcheck")
public class WeatherServiceHealthcheck {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceHealthcheck.class);

    @Autowired
    private MessageSender messageSender;

    @GetMapping
    public void sendSimpleEvent() throws JsonProcessingException {
        LOG.info("sendSimpleEvent() called, sending Message to 'weathersimple:queue'");

        EventSimple eventSimple = new EventSimple();
        eventSimple.setName("foo");

        messageSender.sendMessage(QUEUE_WEATHER_SIMPLE, eventSimple);
    }

    @GetMapping("/event")
    public void sendEvent() throws JsonProcessingException {
        LOG.info("sendEvent() called, sending Message to 'weatherservice:queue'");

        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());
    }

    @GetMapping("/events/{count}")
    public void sendLotsOfEvents(@PathVariable int count) throws JsonProcessingException {
        LOG.info("sendEvent() called, sending Message to 'weatherservice:queue'");

        for (int i = 0; i < count; i++) {
            messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());
        }
    }
}
