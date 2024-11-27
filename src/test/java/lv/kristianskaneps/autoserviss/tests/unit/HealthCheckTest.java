package lv.kristianskaneps.autoserviss.tests.unit;

import io.quarkus.test.junit.QuarkusTest;
import lv.kristianskaneps.autoserviss.controllers.HealthCheckController;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class HealthCheckTest {
    @Test
    public void testHealthCheck() {
        given()
                .when().get("/health-check")
                .then().statusCode(200).body(is(HealthCheckController.HealthStatus.OK.name()));
    }
}
