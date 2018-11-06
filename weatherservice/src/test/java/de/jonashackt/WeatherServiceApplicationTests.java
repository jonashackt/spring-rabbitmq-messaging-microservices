package de.jonashackt;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jonashackt.messaging.MessageSender;
import de.jonashackt.model.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Ignore;
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
import java.time.Instant;
import java.util.Date;

import static de.jonashackt.common.ModelUtil.exampleEventGetOutlook;
import static de.jonashackt.messaging.Queues.QUEUE_WEATHER_BACKEND;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherServiceApplication.class)
public class WeatherServiceApplicationTests {

    @ClassRule
    public static DockerComposeContainer services =
            new DockerComposeContainer(new File("../docker-compose.yml"))
                    .withExposedService("rabbitmq", 5672, Wait.forListeningPort())
                    .withExposedService("weatherbackend", 8090, Wait.forListeningPort());

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Autowired
    private MessageSender messageSender;

    @Ignore
    @Test
    public void is_EventGetOutlook_send_and_EventGeneralOutlook_received() throws JsonProcessingException, InterruptedException {

        // When
        messageSender.sendMessage(QUEUE_WEATHER_BACKEND, exampleEventGetOutlook());

        // Then
        Thread.sleep(2000);

        assertThat(systemOutRule.getLog(), containsString("EventGeneralOutlook received in weatherservice."));
    }

	@Ignore
    @Test
    public void testWithRestAssured() {

		// Given
        Weather weather = new Weather();
        weather.setFlagColor("blue");
        weather.setPostalCode("99425");
        weather.setProduct(Product.ForecastBasic);
        weather.setUser(new User(27, 4300, MethodOfPayment.Bitcoin));

        GeneralOutlook expectedOutlookfromIncredibleBackendLogic = new GeneralOutlook();
        expectedOutlookfromIncredibleBackendLogic.setCity("Weimar");
        expectedOutlookfromIncredibleBackendLogic.setDate(Date.from(Instant.now()));
        expectedOutlookfromIncredibleBackendLogic.setState("Germany");
        expectedOutlookfromIncredibleBackendLogic.setWeatherStation("BestStationInTown");

        // When
        given() // can be ommited when GET only
	        .contentType(ContentType.JSON)
            .body(weather)
        .when() // can be ommited when GET only
            .post("http://localhost:8080/weather/general/outlook")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .assertThat()
                .equals(expectedOutlookfromIncredibleBackendLogic);
    }
}
