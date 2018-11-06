package de.jonashackt.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.service.WeatherBackendService;
import de.jonashackt.model.GeneralOutlook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SERVICE;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SIMPLE;

@Component
public class MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private WeatherBackendService weatherBackendService;

    @Autowired
    private MessageSender messageSender;

    @RabbitListener(queues=QUEUE_WEATHER_BACKEND)
    public void handleMessage(@Payload EventGetOutlook event) throws JsonProcessingException {
        LOG.info("EventGetOutlook received");

        GeneralOutlook generalOutlook = weatherBackendService.generateGeneralOutlook(event.getWeather());

        EventGeneralOutlook eventGeneralOutlook = new EventGeneralOutlook();
        eventGeneralOutlook.setGeneralOutlook(generalOutlook);

        messageSender.sendMessage(QUEUE_WEATHER_SERVICE, eventGeneralOutlook);
    }

    @RabbitListener(queues = QUEUE_WEATHER_SIMPLE)
    public void handleSimpleMessage(@Payload EventSimple event) {
        System.out.println("EventSimple received");
    }
}
