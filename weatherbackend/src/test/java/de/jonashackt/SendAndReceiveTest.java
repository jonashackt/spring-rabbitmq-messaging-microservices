package de.jonashackt;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.EventGetOutlook;
import de.jonashackt.messaging.EventSimple;
import de.jonashackt.messaging.MessageSender;
import de.jonashackt.model.MethodOfPayment;
import de.jonashackt.model.Product;
import de.jonashackt.model.User;
import de.jonashackt.model.Weather;
import org.jetbrains.annotations.NotNull;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_SIMPLE;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherBackendApplication.class)
public class SendAndReceiveTest {

    @ClassRule
    public static DockerComposeContainer services =
            new DockerComposeContainer(new File("../docker-compose.yml"))
                    .withExposedService("rabbitmq", 5672, Wait.forListeningPort());

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Autowired
    private MessageSender messageSender;

    @Test
    public void is_EventSimple_send_and_received_by_MessageHandler() throws JsonProcessingException, InterruptedException {

        EventSimple eventSimple = new EventSimple();
        eventSimple.setName("foo");

        // When
        messageSender.sendMessage(QUEUE_WEATHER_SIMPLE, eventSimple);

        // Then
        Thread.sleep(2000);

        assertThat(systemOutRule.getLog(), containsString("EventSimple received"));
    }

    @Test
    public void is_EventGetOutlook_send_and_received_by_MessageHandler() throws JsonProcessingException, InterruptedException {

        // When
        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());

        // Then
        Thread.sleep(2000);

        assertThat(systemOutRule.getLog(), containsString("EventGetOutlook received"));
    }

    @Test
    public void is_EventGetOutlook_send_and_EventGeneralOutlook_received() throws JsonProcessingException, InterruptedException {

        // When
        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());

        // Then
        Thread.sleep(2000);

        assertThat(systemOutRule.getLog(), containsString("EventGeneralOutlook received."));
    }

    @NotNull
    private EventGetOutlook exampleEventGetOutlook() {
        EventGetOutlook eventGetOutlook = new EventGetOutlook();

        Weather weather = new Weather();
        weather.setFlagColor("blue");
        weather.setPostalCode("99425");
        weather.setProduct(Product.ForecastBasic);
        weather.setUser(new User(27, 4300, MethodOfPayment.Bitcoin));

        eventGetOutlook.setWeather(weather);
        return eventGetOutlook;
    }
}
