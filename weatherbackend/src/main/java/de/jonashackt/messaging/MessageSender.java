package de.jonashackt.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter jsonMessageConverter;

    @PostConstruct
    private void initRabbit() {
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
    }

    public void sendMessage(String queueName, Object event) throws JsonProcessingException {
        LOG.info("Sending event message to Queue '" + queueName + "' with Json: " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(event));
        rabbitTemplate.convertAndSend(queueName, event);
    }
}
