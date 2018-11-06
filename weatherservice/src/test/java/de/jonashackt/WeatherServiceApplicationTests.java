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
