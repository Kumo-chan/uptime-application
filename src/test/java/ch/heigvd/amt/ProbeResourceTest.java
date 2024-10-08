package ch.heigvd.amt;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ProbeResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body(containsString("Welcome to Uptime"));
    }
    @Test
    void testRegiisterEndpoint() {
        given()
                .formParam("url","https://www.example.com")
                .when().post("/probes")
                .then()
                .statusCode(200)
                .body(containsString("https://www.example.com"));
    }

}