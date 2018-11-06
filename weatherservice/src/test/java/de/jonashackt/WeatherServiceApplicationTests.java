package de.jonashackt;

import de.jonashackt.model.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = WeatherServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"server.port=8080"}
)
public class WeatherServiceApplicationTests {
  
	@Test
    public void testWithRestAssured() {

		// Given
        Weather weather = Weather.newBuilder()
                .withFlagColor("blue")
                .withPostalCode("99425")
                .usingProduct(Product.ForecastBasic)
                .withUser(new User(27, 4300, MethodOfPayment.Bitcoin))
                .build();

        GeneralOutlook expectedOutlookfromIncredibleBackendLogic = GeneralOutlook.newBuilder()
                .inCity("Weimar")
                .withDate(Date.from(Instant.now()))
                .inState("Germany")
                .withWeatherStation("BestStationInTown")
                .build();

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
