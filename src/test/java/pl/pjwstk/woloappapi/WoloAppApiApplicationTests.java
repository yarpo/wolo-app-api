package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class WoloAppApiApplicationTests {

	@Test
	public void testTrue() {
		assertTrue(true);
	}

	@Test
	public void testHealthEndpoint() {
		given()
				.when().get("/health")
				.then()
				.statusCode(200);
	}

}
