package de.jonashackt;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = WeatherclientTestApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		properties = {"server.port=8087"}
)
public class WeatherclientTest {

	@Test public void
	should_retrieve_forecast_from_weatherservice() {

		given()
			.contentType(ContentType.JSON)
			.pathParam("zip", "99425")
		.when()
			.get("http://localhost:8087/forecast/{zip}")
		.then()
			.statusCode(HttpStatus.SC_OK);
	}

}
