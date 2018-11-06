package de.jonashackt.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SERVICE;

@Component
public class MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    @RabbitListener(queues=QUEUE_WEATHER_SERVICE)
    public void handleMessage(@Payload EventGeneralOutlook event) throws JsonProcessingException {
        LOG.info("EventGeneralOutlook received in weatherservice. Event has Json: " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(event));
    }
}
