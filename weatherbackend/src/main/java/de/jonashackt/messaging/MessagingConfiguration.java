package de.jonashackt.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.Arrays;
import java.util.List;

import static de.jonashackt.messaging.Queues.*;

@Configuration
@EnableRabbit
public class MessagingConfiguration implements RabbitListenerConfigurer {

    /*
     * Outbound Configuration
     */
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(TOPIC_WEATHER);
    }

    @Bean
    public Queue queueWeatherSimple() {
        return new Queue(QUEUE_WEATHER_SIMPLE, false);
    }

    @Bean
    public Queue queueWeatherBackend() {
        return new Queue(QUEUE_WEATHER_BACKEND, false);
    }

    @Bean
    public Queue queueWeatherService() {
        return new Queue(QUEUE_WEATHER_SERVICE, false);
    }

    @Bean
    public List<Binding> binding() {
        return Arrays.asList(
                BindingBuilder.bind(queueWeatherBackend()).to(exchange()),
                BindingBuilder.bind(queueWeatherService()).to(exchange()));
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
     * Inbound Configuration
     * We are using Annotation-Driven-Message-Listening, described here
     * http://docs.spring.io/spring-amqp/reference/htmlsingle/#async-annotation-driven
     */
    @Autowired
    public ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    public MessageHandler eventResultHandler() {
        return new MessageHandler();
    }

    @Override
    public void configureRabbitListeners(
            RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }
}
