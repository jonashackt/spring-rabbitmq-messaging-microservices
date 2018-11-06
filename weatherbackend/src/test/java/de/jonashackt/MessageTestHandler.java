package de.jonashackt;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.EventGeneralOutlook;
import de.jonashackt.messaging.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SERVICE;

@Component
public class MessageTestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    @RabbitListener(queues=QUEUE_WEATHER_SERVICE)
    public void handleMessage(@Payload EventGeneralOutlook event) throws JsonProcessingException {
        LOG.info("EventGeneralOutlook received.");
    }
}
