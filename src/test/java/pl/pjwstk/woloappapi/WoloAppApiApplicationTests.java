package pl.pjwstk.woloappapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WoloAppApiApplicationTests {

	@Test
	public void testHealthEndpoint() {
		given()
				.when().get("/health")
				.then()
				.statusCode(200);
	}
}
