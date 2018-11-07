package de.jonashackt;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.MessageSender;
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

import static de.jonashackt.common.ModelUtil.exampleEventGetOutlook;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherServiceApplication.class)
public class WeatherServiceSendAndReceiveTest {

    @ClassRule
    public static DockerComposeContainer services =
            new DockerComposeContainer(new File("../docker-compose.yml"))
                    .withExposedService("rabbitmq", 5672, Wait.forListeningPort())
                    .withExposedService("weatherbackend", 8090, Wait.forListeningPort());

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Autowired
    private MessageSender messageSender;

    @Test
    public void is_EventGetOutlook_send_and_EventGeneralOutlook_received() throws JsonProcessingException, InterruptedException {

        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());

        Thread.sleep(5000); // We have to wait a bit here, since our Backend needs 3+ seconds to calculate the outlook

        assertThat(systemOutRule.getLog(), containsString("EventGeneralOutlook received in weatherservice."));
    }
}
