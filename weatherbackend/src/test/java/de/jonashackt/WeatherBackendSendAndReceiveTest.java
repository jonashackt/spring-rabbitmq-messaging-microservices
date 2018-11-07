package de.jonashackt;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.EventSimple;
import de.jonashackt.messaging.MessageSender;
import org.junit.ClassRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import static de.jonashackt.common.ModelUtil.exampleEventGetOutlook;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SIMPLE;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherBackendApplication.class)
@ContextConfiguration(initializers = {WeatherBackendSendAndReceiveTest.Initializer.class})
public class WeatherBackendSendAndReceiveTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbitMq.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbitMq.getMappedPort(5672))
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @ClassRule
    public static GenericContainer rabbitMq = new GenericContainer("rabbitmq:management")
            .withExposedPorts(5672)
            .waitingFor(Wait.forListeningPort());

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Autowired
    private MessageSender messageSender;

    @Test
    public void is_EventSimple_send_and_received_by_MessageHandler() throws JsonProcessingException, InterruptedException {

        EventSimple eventSimple = new EventSimple();
        eventSimple.setName("foo");

        messageSender.sendMessage(QUEUE_WEATHER_SIMPLE, eventSimple);

        Thread.sleep(2000);

        assertThat(systemOutRule.getLog(), containsString("EventSimple received"));
    }

    @Test
    public void is_EventGetOutlook_send_and_received_by_MessageHandler() throws JsonProcessingException, InterruptedException {

        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());

        Thread.sleep(2000);

        assertThat(systemOutRule.getLog(), containsString("EventGetOutlook received"));
    }

    @Test
    public void is_EventGetOutlook_send_and_EventGeneralOutlook_received() throws JsonProcessingException, InterruptedException {

        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());

        Thread.sleep(4000);

        assertThat(systemOutRule.getLog(), containsString("EventGeneralOutlook received."));
    }
}
