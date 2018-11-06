package de.jonashackt.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.controller.WeatherBackendController;
import de.jonashackt.model.GeneralOutlook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SERVICE;

@Component
public class MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private WeatherBackendController weatherBackendController;

    @Autowired
    private MessageSender messageSender;

    @RabbitListener(queues=QUEUE_WEATHER_BACKEND)
    public void handleMessage(@Payload EventGetOutlook event) throws JsonProcessingException {
        LOG.info("EventGetOutlook received");

        GeneralOutlook generalOutlook = weatherBackendController.generateGeneralOutlook(event.getWeather());

        messageSender.sendMessage(QUEUE_WEATHER_SERVICE, generalOutlook);
    }
}
